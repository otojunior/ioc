package com.github.otojunior;

import java.util.HashMap;
import java.util.Map;

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
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		var object = components.get(clazz);
		if (object == null) {
			throw new RuntimeException("Class not registered: " + clazz);
		}
		return (T)object;
	}

	/**
	 * Initialize the IoC
	 */
	public void init() {
		for (Object component : components.values()) {
			for (Class<?> c = component.getClass();
					c != Object.class;
					c = c.getSuperclass()) {
				for (Class<?> i : c.getInterfaces()) {
					if (i.equals(Injectable.class)) {
						((Injectable)component).injects(this);
					}
				}
			}
		}
	}

	/**
	 * Register a component
	 * @param <T> Type of component.
	 * @param clazz Class of the component.
	 * @return this IoC
	 */
	public Ioc register(Object object) {
		components.put(object.getClass(), object);
        return this;
    }
}
