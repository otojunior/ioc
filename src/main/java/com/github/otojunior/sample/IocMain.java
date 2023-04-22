/**
 * 
 */
package com.github.otojunior.sample;

import org.tinylog.Logger;

import com.github.otojunior.Ioc;

/**
 * IoC Main Class
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 */
public class IocMain {
	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		var t1 = System.nanoTime();

		Ioc.getInstance()
			.register(new SampleService())
			.register(new SampleController())
			.init();

		var controller = Ioc.getInstance().get(SampleController.class);
		controller.doSomething();

		var t2 = System.nanoTime();
		Logger.debug("Execution time {} ms", ((t2-t1)/1000/1000));
	}
}
