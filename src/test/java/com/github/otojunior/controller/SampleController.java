/**
 * 
 */
package com.github.otojunior.controller;

import com.github.otojunior.Component;
import com.github.otojunior.Inject;
import com.github.otojunior.service.SampleService;

/**
 * Sample Service.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 */
@Component
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
