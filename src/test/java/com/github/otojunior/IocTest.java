/**
 * 
 */
package com.github.otojunior;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.otojunior.controller.SampleController;

/**
 * {@link Ioc} unit test.
 * @author Oto Soares Coelho Junior
 * @since 22/04/2023
 */
class IocTest {
	private static Ioc ioc;
	
	@BeforeAll
	static void setup() {
		ioc = Ioc.getInstance();
		ioc.init(Ioc.class);
	}
	
	/**
	 * Test method for {@link com.github.otojunior.Ioc}.
	 */
	@Test
	void test() {
		ioc.get(SampleController.class).doSomething();
	}
}
