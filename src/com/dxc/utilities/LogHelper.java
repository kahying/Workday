package com.dxc.utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;

public class LogHelper {

	private static ExtentTest testLogger;
	
	public static void setTestLogger(ExtentTest testLogger) {
		LogHelper.testLogger = testLogger;
	}

	public static void setInfoLog(String log) {
		testLogger.log(Status.INFO, log);		
	}
	
	public static void setFatalLog(String log) {
		testLogger.log(Status.FATAL, log);		
	}
	
	public static void setDebugLog(String log) {
		testLogger.log(Status.DEBUG, log);		
	}
	
	public static void setErrorLog(String log) {
		testLogger.log(Status.ERROR, log);		
	}
	
	public static void setFailLog(String log) {
		testLogger.log(Status.FAIL, log);		
	}
	
	public static void setPassLog(String log) {
		testLogger.log(Status.PASS, log);		
	}
	
	public static void setSkipLog(String log) {
		testLogger.log(Status.SKIP, log);		
	}
	
	public static void setWarningLog(String log) {
		testLogger.log(Status.WARNING, log);		
	}

	public static void setInfoLog(String log, MediaEntityModelProvider mediaBuild) {
		testLogger.log(Status.INFO, log, mediaBuild);
	}

}
