/**
 * 
 */
package com.github.otojunior.sample;

import javax.enterprise.context.ApplicationScoped;

import org.tinylog.Logger;

/**
 * Sample Service.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 */
@ApplicationScoped
public class SampleService {
	public void call() {
		Logger.info("Service call");
	}
}
