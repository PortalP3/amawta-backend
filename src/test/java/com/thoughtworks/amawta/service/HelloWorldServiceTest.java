package com.thoughtworks.amawta.service;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HelloWorldServiceTest {

	@Test()
	public void shouldReturnTrueWhenHelloMessageIsNotNull() {
		HelloWorldService hello = new HelloWorldService();
		String helloMsg = hello.generate();
		Assert.assertNotNull(helloMsg);
	}

	@Test()
	public void shouldReturnHelloWorldMessageWhenHelloWorldIsCreated() {
		HelloWorldService hello = new HelloWorldService();
		String helloMsg = hello.generate();
		Assert.assertEquals(helloMsg, "Hello World");
	}
}
