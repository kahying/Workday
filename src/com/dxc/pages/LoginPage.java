package com.dxc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.dxc.utilities.Constants;
import com.dxc.utilities.LogHelper;
import com.dxc.utilities.WebElementHelper;
import com.dxc.utilities.Utility;

public class LoginPage {

	private String firstLoadedPage;

	/*
	 * Defining the locators for all the elements in LoginPage
	 */
	By imageWorkdayLocator = By.xpath("//img[@alt='workday']");
	By labelHPESSOHeaderLocator = By.className("hpnn-header-logo");
	By inputHPESSOUsernameLocator = By.id("username");
	By inputHPESSOPasswordLocator = By.id("password");
	By buttonHPESSOLogonLocator = By.xpath("//input[@value='Log on']");
	By inputWorkdayUsernameLocator = By.xpath("//div[@data-automation-id='userName']/input");
	By inputWorkdayPasswordLocator = By.xpath("//div[@data-automation-id='password']/input");
	By buttonWorkdaySignInLocator = By.xpath("//button[@data-automation-id='goButton']");	

	public LoginPage() {
		LogHelper.setInfoLog("Start verify LoginPage is visible");
		
		/*
		 * First thing to always check whether the login page is visible 
		*/
		WebDriverWait wait = WebElementHelper.setWebDriverWait(10);
		
		System.out.println("Enter here 1");
		Boolean mainPageElementVisible = wait
				.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(labelHPESSOHeaderLocator),
						ExpectedConditions.visibilityOfElementLocated(imageWorkdayLocator)));
		System.out.println("Enter here 2 " + mainPageElementVisible);
		
		if (mainPageElementVisible) {
			System.out.println("Enter here 3");
			if (WebElementHelper.isElementPresent(imageWorkdayLocator, false)) {
				System.out.println("Enter here 4");
				firstLoadedPage = "Workday Login";
				LogHelper.setInfoLog("Redirected to Workday Login page");
			} else if (WebElementHelper.isElementPresent(labelHPESSOHeaderLocator, false)) {
				System.out.println("Enter here 5");
				firstLoadedPage = "HPE SSO";
				LogHelper.setInfoLog("Redirected to HPE SSO Login page");
				
				String browserTitle = WebElementHelper.getBrowserTitle();
				
				if(browserTitle.contains("HPE Log on"))
					LogHelper.setPassLog(browserTitle +" contain HPE Log on");
				else
					LogHelper.setFailLog(browserTitle +" does not contain HPE Log on");
			}
			System.out.println("Enter here 6");
		}
	}

	/*
	 * Logging into Workday with 3 different possibilities (HPE SSO/Workday Login Page/DXC SSO)
	*/
	public LoginPage loginWorkday() {
		String usernameFromProperties;
		String passwordFromProperties;
		
		switch (firstLoadedPage) {
		case "HPE SSO":
			System.out.println("HPE SSO ");
			usernameFromProperties = Utility.getHPESSOUsernameFromProperties(Constants.CONFIG_PROPERTIES);
			passwordFromProperties = Utility.getHPESSOPasswordFromProperties(Constants.CONFIG_PROPERTIES);
			
			setUsername(inputHPESSOUsernameLocator, usernameFromProperties, true, "Input" + " " + usernameFromProperties + " into username field");
			setPassword(inputHPESSOPasswordLocator, passwordFromProperties, true, "Input" + " " + passwordFromProperties + " into password field");
			clickLoginButton(buttonHPESSOLogonLocator, true, "Login button is clicked");

			break;
		case "Workday Login":
			System.out.println("Workday Login");
			usernameFromProperties = Utility.getWorkdayUsernameFromProperties(Constants.CONFIG_PROPERTIES);
			passwordFromProperties = Utility.getWorkdayPasswordFromProperties(Constants.CONFIG_PROPERTIES);
			
			setUsername(inputWorkdayUsernameLocator, usernameFromProperties, true, "Input" + " " + usernameFromProperties + " into username field");
			setPassword(inputWorkdayPasswordLocator, passwordFromProperties, true, "Input" + " " + passwordFromProperties + " into password field");
			clickLoginButton(buttonWorkdaySignInLocator, true, "Login button is clicked");

			break;
		case "SSO":
			System.out.println("DXC SSO");

			break;
		}

		// return new HomePage(driver);
		return this;
	}

	/**
	 * Insert value into the username field
	 * 
	 * @param driver
	 * @param elementLocator		:Locator of the username input field
	 * @param username				:Value to be insert into username input field
	 * @param mandatoryElement		:Is it mandatory for the username to exist in order to proceed.
	 * @param log 
	 * @return						:Returning back the LoginPage object.
	 */
	public LoginPage setUsername(By elementLocator, String username, boolean mandatoryElement, String log) {
		WebElementHelper.setText(elementLocator, username, mandatoryElement, log);
		return this;
	}
	
	/**
	 * Insert value into the password field
	 * 
	 * @param driver
	 * @param elementLocator		:Locator of the password input field
	 * @param password				:Value to be insert into password input field
	 * @param mandatoryElement		:Is it mandatory for the password to exist in order to proceed.
	 * @param log 
	 * @return						:Returning back the LoginPage object.
	 */
	public LoginPage setPassword(By elementLocator, String password, boolean mandatoryElement, String log) {

		WebElementHelper.setText(elementLocator, password, mandatoryElement, log);
		return this;
	}

	/**
	 * The login page allows the user to submit the login form by clicking on the login button
	 * 
	 * @param driver
	 * @param elementLocator		:Locator of the button field
	 * @param mandatoryElement		:Is it mandatory for the button to exist in order to proceed.
	 * @param log 
	 * @return						:Returning back the LoginPage object.
	 */
	public HomePage clickLoginButton(By elementLocator, boolean mandatoryElement, String log) {
		WebElementHelper.clickElement(elementLocator, mandatoryElement, log);
		return new HomePage();
	}

	// The login page allows the user to submit the login form knowing that an invalid username and / or password were entered
//	public LoginPage submitLoginExpectingFailure() {
//
//		//driver.findElement(buttonLoginLocator).submit();
//
//		// Return a new page object representing the destination. If the login with credentials expected to fail login,
//		// the script will fail when it attempts to instantiate the LoginPage PageObject.
//		return new LoginPage(driver);
//	}
}
