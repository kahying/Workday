package com.dxc.utilities;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class WebElementHelper {

	private static WebDriver driver;

	public static void setText(By elementLocator, String inputValue, boolean mandatoryElement, String log) {

		if (isElementPresent(elementLocator, mandatoryElement)) {
			driver.findElement(elementLocator).clear();
			driver.findElement(elementLocator).sendKeys(inputValue);

			LogHelper.setInfoLog(log);
			
			Utility.takeScreenshot();
		} else {
			// TODO: Log not found and no input
		}
	}

	public static void clickElement(By elementLocator, boolean mandatoryElement, String log) {

		if (isElementPresent(elementLocator, mandatoryElement)) {
			driver.findElement(elementLocator).click();
			LogHelper.setInfoLog(log);
		} else {
			// TODO: Log not found and not clicked
		}
	}

	public static boolean isElementPresent(By elementLocator, boolean mandatoryElement) {

		Wait<WebDriver> wait;
		final long startTime = System.currentTimeMillis();

		if (mandatoryElement) {
			wait = new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
		} else {
			wait = new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS);
		}

		WebElement we = null;

		while ((System.currentTimeMillis() - startTime) < 20000) {
			try {
				we = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
				break;
			} catch (NoSuchElementException e) {
			}
		}

		if (we != null && we.isDisplayed()) {
			LogHelper.setInfoLog("Element " + elementLocator + " found.");
			return true;
		} else {
			LogHelper.setInfoLog("Element " + elementLocator + " not found!");
			return false;
		}
	}
	
	public static String getElementText(By buttonCurrentAppLocator, boolean mandatoryElement, String log) {
		String elementTextvalue = null;
		
		if(isElementPresent(buttonCurrentAppLocator, mandatoryElement)){
			elementTextvalue = driver.findElement(buttonCurrentAppLocator).getText();
			LogHelper.setInfoLog(log);
		}
		return elementTextvalue;
	}

	public static void clickLinkText(String linkTextName, boolean mandatoryElement, String log) {
		if(isElementPresent(By.linkText(linkTextName), mandatoryElement)){
			driver.findElement(By.linkText(linkTextName)).click();
		}
	}
	
	public static void setDriver(WebDriver driver) {
		WebElementHelper.driver = driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static WebDriverWait setWebDriverWait(int seconds) {
		return new WebDriverWait(driver, seconds);
	}

	public static String getBrowserTitle() {
		return driver.getTitle();
	}
}
