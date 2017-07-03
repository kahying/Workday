package com.dxc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.dxc.utilities.LogHelper;

public class HomePage {

	By userNavigationButton = By.id("userNavButton");

	public HomePage() {
		LogHelper.setInfoLog("Start verify HomePage is visible");
	}
}
