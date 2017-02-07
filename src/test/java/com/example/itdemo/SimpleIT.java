package com.example.itdemo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class SimpleIT extends TestBenchTestCase {

	private int jettyPort = 8080;

	@Before
	public void setup() throws MalformedURLException {
		String desiredDriver = System.getProperty("webdriver");
		if (desiredDriver == null || "PhantomJS".equalsIgnoreCase(desiredDriver)) {
			setDriver(new PhantomJSDriver());
		}
		else if("Firefox".equalsIgnoreCase(desiredDriver)) {
			setDriver(new FirefoxDriver());
		}
		else if("RemoteWebDriver".equalsIgnoreCase(desiredDriver)) {
			new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.internetExplorer());
		}
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
			String portStr = prop.getProperty("jetty.port");
			jettyPort = Integer.parseInt(portStr);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testGreeting() {
		getDriver().get("http://localhost:" + jettyPort);
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
