package com.example.itdemo;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class SimpleIT extends TestBenchTestCase {

	private String jettyUrl;

	@Rule
	public ScreenshotOnFailureRule screenshotOnFailure = new ScreenshotOnFailureRule(this, true);

	@Before
	public void setup() throws MalformedURLException {
		String desiredDriver = System.getProperty("webdriver");
		/**
		 * URL to use when creating RemoteWebDriver, e.g.
		 * http://localhost:4444/wd/hub
		 */
		String tbGridUrl = System.getProperty("tbgridurl");
		if (desiredDriver == null || "PhantomJS".equalsIgnoreCase(desiredDriver)) {
			setDriver(new PhantomJSDriver());
		} else if ("Firefox".equalsIgnoreCase(desiredDriver)) {
			setDriver(new FirefoxDriver());
		} else if ("RemoteWebDriver".equalsIgnoreCase(desiredDriver)) {
			if (tbGridUrl == null) {
				throw new RuntimeException("RemoteWebDriver requested, but tbgridurl system property was not set");
			}
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			// without requireWindowFocus the driver pauses for about 2 seconds
			// between each character it types
			capabilities.setCapability("requireWindowFocus", true);
			RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(tbGridUrl), capabilities);
			setDriver(remoteWebDriver);
		}
		jettyUrl = System.getProperty("jettyurl");
		if (jettyUrl == null) {
			jettyUrl = "http://localhost:8080";
		}
	}

	@Test
	public void testGreeting() {
		getDriver().get(jettyUrl);
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
		getDriver().quit();
	}
}
