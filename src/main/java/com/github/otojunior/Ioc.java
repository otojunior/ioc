package com.github.otojunior;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.tinylog.Logger;

/**
 * IoC Container.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 */
public class Ioc {
	private static Ioc instance;

	/**
	 * Create instance of Ioc.
	 * @return {@link Ioc}
	 */
	public static Ioc getInstance() {
		if (instance == null) {
			instance = new Ioc();
		}
		return instance;
	}

	private Map<Class<?>, Object> components = new HashMap<>();

	/**
	 * Private Constructor.
	 */
	private Ioc() {	}

	/**
	 * Gets an instance of object from IoC.
	 * @param <T> Type Class type.
	 * @param clazz Class name.
	 * @return a instance of object.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		var object = components.get(clazz);
		if (object == null) {
			throw new RuntimeException("Class not registered: " + clazz);
		}
		return (T) object;
	}

	/**
	 * Initialize the IoC
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws InstantiationException 
	 */
	public <T> void init(Class<T> baseclass) {
		try {
			var classes = getClasses(baseclass.getPackageName());

			for (var clazz : classes) {
				if (clazz.isAnnotationPresent(Component.class)) {
					if (Logger.isDebugEnabled()) {
						Logger.debug("Component found: {}", clazz.getName());
					}
					register(clazz);
				}
			}

			for (var component : components.values()) {
				for (var field : component.getClass().getDeclaredFields()) {
					if (field.isAnnotationPresent(Inject.class)) {
						var dependency = components.get(field.getType());
						if (dependency == null) {
							var msg = "Unsatisfied dependency: " + field.getType().getName();
							throw new IllegalStateException(msg);
						}
						field.setAccessible(true);
						field.set(component, dependency);
					}
				}
			}
		} catch (Exception e) {
			Logger.error(e);
		}
	}

	/**
	 * Get all classes based on package name.
	 * @param packageName Package name for scan.
	 * @return List of classes.
	 */
	private List<Class<?>> getClasses(String packageName) {
        var classes = new ArrayList<Class<?>>();
        var path = packageName.replace('.', '/');
        var classLoader = Thread
    		.currentThread()
    		.getContextClassLoader();
        try {
            var resources = new ArrayList<String>();
            for (var url : Collections.list(classLoader.getResources(path))) {
                resources.add(url.getFile());
            }
            for (var resource : resources) {
                if (resource.contains(".jar")) {
                    classes.addAll(getClassesFromJar(resource, packageName));
                } else {
                    classes.addAll(getClassesFromDirectory(
                		new File(resource),
                		packageName));
                }
            }
        } catch (IOException e) {
            Logger.error(e);
        }
        return classes;
    }

	/**
     * Get classes from directory.
     * @param directory directory name.
     * @param packageName package name.
     * @return List of classes from directory.
     */
    private List<Class<?>> getClassesFromDirectory(
    		final File directory,
    		final String packageName) {
        var classes = new ArrayList<Class<?>>();
        if (directory.exists()) {
        	for (var file : directory.listFiles()) {
                if (file.isDirectory()) {
                    classes.addAll(getClassesFromDirectory(file, packageName
                		+ "."
                		+ file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    var className = packageName
                		+ '.'
                		+ file.getName().substring(0, file.getName().length() - 6);
                    try {
                        classes.add(Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        Logger.error(e);
                    }
                }
            }
        }
        return classes;
    }

	/**
	 * 
	 * @param jarPath
	 * @param packageName
	 * @return
	 * @throws IOException
	 */
    private List<Class<?>> getClassesFromJar(String jarPath, String packageName) {
        var classes = new ArrayList<Class<?>>();
        try (var jarFile = new JarInputStream(new FileInputStream(jarPath))) {
            JarEntry entry;
            while ((entry = jarFile.getNextJarEntry()) != null) {
                var name = entry.getName().replace('/', '.');
                if (name.startsWith(packageName) && name.endsWith(".class")) {
                    var className = name.substring(0, name.length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        } catch (ClassNotFoundException | IOException e) {
			Logger.error(e);
		}
        return classes;
    }

    /**
	 * Register a component
	 * 
	 * @param <T>   Type of component.
	 * @param clazz Class of the component.
	 * @return this IoC
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private <T> Ioc register(Class<T> clazz) {
		try {
			components.put(clazz, clazz
				.getDeclaredConstructor()
				.newInstance());
		} catch (Exception e) {
			Logger.error(e);
			throw new RuntimeException(e);
		}
		return this;
	}
}
