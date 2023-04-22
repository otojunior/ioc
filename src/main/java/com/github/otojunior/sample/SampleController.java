/**
 * 
 */
package com.github.otojunior.sample;

import com.github.otojunior.Inject;

/**
 * Sample Service.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 *
 */
public class SampleController {
	@Inject
	private SampleService service;

	/**
	 * Sample method to exemplificate call.
	 */
	public void doSomething() {
		service.doSomething();
	}
}
