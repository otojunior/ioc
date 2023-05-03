/**
 * 
 */
package com.github.otojunior.sample;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.tinylog.Logger;

/**
 * Sample Service.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 *
 */
@ApplicationScoped
public class SampleController {
	@Inject
	private SampleService service;

	/**
	 * Sample method to exemplificate call.
	 */
	public void call() {
		Logger.info("Controller call");
		service.call();
	}
}
