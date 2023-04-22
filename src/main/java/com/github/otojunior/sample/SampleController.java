/**
 * 
 */
package com.github.otojunior.sample;

import com.github.otojunior.Injectable;
import com.github.otojunior.Ioc;

/**
 * Sample Service.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 *
 */
public class SampleController implements Injectable {
	private SampleService service;

	/**
	 * Sample method to exemplificate call.
	 */
	public void doSomething() {
		service.doSomething();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void injects(Ioc ioc) {
		this.service = ioc.get(SampleService.class);
	}
}
