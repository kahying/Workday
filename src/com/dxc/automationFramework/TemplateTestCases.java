package com.dxc.automationFramework;

import static org.testng.Assert.fail;

import java.io.File;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.dxc.pages.LoginPage;
import com.dxc.utilities.ExtentHelper;
import com.dxc.utilities.Utility;

public class TemplateTestCases {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ExtentReports extent;
	private ExtentTest testLogger;
	private String browser;
	private String environment;

	@Parameters({ "browser", "environment" })
	@BeforeClass
	public void setUp(String browser, String environment) throws Exception {
		extent = ExtentHelper.getExtent("This is the HTML title", "This is the report name");
		this.browser = browser;
		this.environment = environment;
	}

	@Test
	public void testLoginPageTestNG() throws Exception {
		
		// creates a toggle for the given test, adds all log events under it
		testLogger = extent.createTest("TestName", "this is the test description").assignCategory("We can categorize this item!");
		driver = Utility.initializeTest(browser, testLogger, environment);
		Utility.loadWorkdayURL();
		
	}

	// @Test
	// public void checkFail(){
	// testLogger = extent.createTest("Testing how fail works");
	// //testLogger.log(Status.INFO, "fail check started");
	// testLogger.fail("Test fail");
	// }

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		extent.flush();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
		// TODO: Think of where to call this.
//		Utility.zipReport();
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
