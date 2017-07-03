package com.dxc.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentHelper {

	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;

	public static ExtentReports getExtent(String docTitle, String reportName) {
		// avoid creating new instance of html file
		if (extent != null)
			return extent; 
		
		extent = new ExtentReports();
		extent.attachReporter(getHtmlReporter(docTitle, reportName));
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter(String docTitle, String reportName) {

		htmlReporter = new ExtentHtmlReporter(Constants.EXTENT_REPORT_PATH + Constants.EXTENT_REPORT_NAME);
		
		//This allows logs to keep appending the html
		htmlReporter.setAppendExisting(true);
		
		// This is to load all document title, report name in config.xml
		// htmlReporter.loadXMLConfig("./config.xml");

		// make the charts visible on report open
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle(docTitle);
		htmlReporter.config().setReportName(reportName);
		
		return htmlReporter;
	}

	public static ExtentTest createTest(String name, String description) {
		test = extent.createTest(name, description);
		return test;
	}
}
