/**
 * 
 */
package com.github.otojunior.service;

import org.tinylog.Logger;

import com.github.otojunior.Component;

/**
 * Sample Service.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 */
@Component
public class SampleService {
	public void doSomething() {
		Logger.info("Doing something...");
	}
}
