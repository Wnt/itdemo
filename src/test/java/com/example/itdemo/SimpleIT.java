package com.example.itdemo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class SimpleIT extends TestBenchTestCase {

	private RemoteWebDriver driver;

	@Before
	public void setup() {
		driver = new FirefoxDriver();
		setDriver(driver);
	}

	@Test
	public void testGreeting() {
		driver.get("http://localhost:8080");
		TextFieldElement typeyournamehereTextField = $(TextFieldElement.class).caption("Type your name here:").first();
		typeyournamehereTextField.setValue("foobar");
		ButtonElement clickMeButton = $(ButtonElement.class).caption("Click Me").first();
		clickMeButton.click();
		ElementQuery<LabelElement> labelQuery = $(LabelElement.class);
		Assert.assertTrue("Label should be added to the UI", labelQuery.exists());
		LabelElement label1 = labelQuery.first();
		Assert.assertTrue("Greeting should contain the inputted value 'foobar'", label1.getHTML().contains("foobar"));
	}
	
	@After
	public void teardown() {
		driver.close();
	}
}
