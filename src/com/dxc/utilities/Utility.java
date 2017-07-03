package com.dxc.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Utility {
	private static WebDriver driver;
	private static String environment;

	/**
	 * 
	 * @param browser Choice of browser that is set in testng.xml
	 * @param testLogger
	 * @return The driver of the selected browser
	 */
	public static WebDriver initializeTest(String browser, ExtentTest testLogger, String environmnet) {
		setEnvironment(environmnet);
//		Listener.setTestLogger(testLogger);
		LogHelper.setTestLogger(testLogger);
		driver = loadBrowserDriver(browser);
		WebElementHelper.setDriver(driver);
		
		return driver;
	}

	/**
	 * 
	 * @param browserName 	Name of Browser to test with, that is specify in config.properties.
	 * @return 				WebDriver of the selected browser.
	 */
	private static WebDriver loadBrowserDriver(String browserName) {

		switch (browserName) {
		case Constants.FIREFOX:
			// Setting system property for driver to run test in Firefox.
			System.setProperty("webdriver.gecko.driver", "lib\\geckodriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver();

			LogHelper.setInfoLog("Firefox driver instantiated");
			break;

		case Constants.INTERNET_EXPLORER:
			// Setting system property for driver to run test in Internet Explorer.
			System.setProperty("webdriver.ie.driver", "lib\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

			LogHelper.setInfoLog("Internet Explorer driver instantiated");
			break;

		default:
			// Default load Chrome browser
			LogHelper.setInfoLog("Invalid browser specified. Default Chrome browser will be selected");
		
		case Constants.GOOGLE_CHROME:
			// Setting system property for driver to run test in Google Chrome.
			System.setProperty("webdriver.chrome.driver", "lib\\chromedriver.exe");
			driver = new ChromeDriver();

			LogHelper.setInfoLog("Chrome driver instantiated");
			break;
		}
		driver.manage().window().maximize();

		return driver;
	}

	public static void loadWorkdayURL() {
		String url = getWorkdayURL(environment);
		driver.get(url + "/");

		LogHelper.setInfoLog("Loading " + url + " URL in browswer");
	}

	/*
	 * retrieve the URL based on the environment which is stored in config.properties
	 */
	private static String getWorkdayURL(String environment) {
		Properties prop = getProperties(Constants.CONFIG_PROPERTIES);
		return prop.getProperty(environment + "URL");
	}

	/*
	 * retrieve the Username for HPE SSO login page based on the environment which is stored in config.properties
	 */
	public static String getHPESSOUsernameFromProperties(String configProperties) {
		Properties prop = getProperties(configProperties);
		return prop.getProperty(environment + "HPESSOUsername");
	}

	/*
	 * retrieve the Password for HPE SSO login page based on the environment which is stored in config.properties
	 */
	public static String getHPESSOPasswordFromProperties(String configProperties) {
		Properties prop = getProperties(configProperties);
		return prop.getProperty(environment + "HPESSOPassword");
	}

	/*
	 * retrieve the Username for Workday login page based on the environment which is stored in config.properties
	 */
	public static String getWorkdayUsernameFromProperties(String configProperties) {
		Properties prop = getProperties(configProperties);
		return prop.getProperty(environment + "WorkdayUsername");
	}

	/*
	 * retrieve the Password for Workday login page based on the environment which is stored in config.properties
	 */
	public static String getWorkdayPasswordFromProperties(String configProperties) {
		Properties prop = getProperties(configProperties);
		return prop.getProperty(environment + "WorkdayPassword");
	}

	/**
	 * 
	 * @param propertyName specify the name of the .properties file to be retrieved
	 * @return
	 */
	private static Properties getProperties(String propertyName) {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(propertyName);

			// load a properties file
			prop.load(input);
			LogHelper.setInfoLog("Loading " + propertyName + " file.");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public static void takeScreenshot() {
		String name = Constants.SCREENSHOT_PATH + Utility.getTimestamp() + ".png";
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		try {
			ImageIO.write(fpScreenshot.getImage(), "PNG", new File(name));
			LogHelper.setInfoLog("Screenshot from : " + name, MediaEntityBuilder.createScreenCaptureFromPath("../../" + name).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTimestamp() {
		return new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
	}

	public static void zipReport() {
		/*
        File file = new File("/Users/pankaj/sitemap.xml");
        String zipFileName = "/Users/pankaj/sitemap.zip";
        ZipHelper.zipSingleFile(file, zipFileName);
        */
		
		File dir = new File(Constants.REPORT_DIR);
        String zipDirName = Constants.REPORT_ZIP_NAME;
        
        ZipHelper.zipDirectory(dir, zipDirName);		
	}
	
	public static String getEnvironment() {
		return environment;
	}

	public static void setEnvironment(String environment) {
		Utility.environment = environment;
	}
}
