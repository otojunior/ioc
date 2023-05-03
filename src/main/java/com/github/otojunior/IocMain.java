/**
 * 
 */
package com.github.otojunior;

import javax.enterprise.inject.se.SeContainerInitializer;

import org.tinylog.jul.JulTinylogBridge;

import com.github.otojunior.sample.SampleController;

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
		JulTinylogBridge.activate();

		try(var container = SeContainerInitializer
				.newInstance()
				.initialize()) {
			var controller = container
				.select(SampleController.class)
				.get();
			
			controller.call();
	    }
	}
}
