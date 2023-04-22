/**
 * 
 */
package com.github.otojunior.sample;

import org.tinylog.Logger;

import com.github.otojunior.Injectable;
import com.github.otojunior.Ioc;

/**
 * Sample Service.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 */
public class SampleService implements Injectable{
	public void doSomething() {
		Logger.info("Doing something...");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void injects(Ioc ioc) { /* No dependencies for this class. */ }
}
