package com.dxc.utilities;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class Listener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	private static ExtentTest testLogger;

	/*
	 * This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
	 */
	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		//testLogger.log(Status.INFO, "About to begin executing following method : " + returnMethodName(arg0.getTestMethod()));
	}

	/*
	 * This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
	 */
	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		//testLogger.log(Status.INFO, "Completed executing following method : " + returnMethodName(arg0.getTestMethod()));
	}

	/*
	 * This belongs to ISuiteListener and will execute before the Suite start
	 */
	@Override
	public void onStart(ISuite arg0) {
		//testLogger.log(Status.INFO, "About to begin executing Suite " + arg0.getName());
	}

	/*
	 * This belongs to ISuiteListener and will execute, once the Suite is finished
	 */
	@Override
	public void onFinish(ISuite arg0) {
		//testLogger.log(Status.INFO, "About to end executing Suite " + arg0.getName());
	}

	/*
	 * This belongs to ITestListener and will execute before starting of Test set/batch
	 */
	@Override
	public void onStart(ITestContext arg0) {
		//testLogger.log(Status.INFO, "About to begin executing Test " + arg0.getName());
	}

	/*
	 * This belongs to ITestListener and will execute, once the Test set/batch is finished
	 */
	@Override
	public void onFinish(ITestContext arg0) {
		//testLogger.log(Status.INFO, "Completed executing test " + arg0.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
	}

	/*
	 * This belongs to ITestListener and will execute only on the event of fail test
	 */
	@Override
	public void onTestFailure(ITestResult arg0) {
		// This is calling the printTestResults method
		Utility.takeScreenshot();
		printTestResults(arg0);
	}

	/*
	 * This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
	 */
	@Override
	public void onTestSkipped(ITestResult arg0) {
		printTestResults(arg0);
	}

	/*
	 * This belongs to ITestListener and will execute before the main test start (@Test)
	 */
	@Override
	public void onTestStart(ITestResult arg0) {
		//testLogger.log(Status.INFO, "The execution of the main test starts now");
	}

	/*
	 * This belongs to ITestListener and will execute only when the test is pass
	 */
	@Override
	public void onTestSuccess(ITestResult arg0) {
		// This is calling the printTestResults method
		printTestResults(arg0);
	}

	/*
	 * This is the method which will be executed in case of test pass or fail This will provide the information on the test
	 */
	private void printTestResults(ITestResult result) {

		//testLogger.log(Status.INFO, "Test Method resides in " + result.getTestClass().getName());

		if (result.getParameters().length != 0) {
			String params = null;
			for (Object parameter : result.getParameters()) {
				params += parameter.toString() + ",";
			}
			//testLogger.log(Status.INFO, "Test Method had the following parameters : " + params);
		}
		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			break;
		case ITestResult.FAILURE:
			status = "Failed";
			break;
		case ITestResult.SKIP:
			status = "Skipped";
		}
		//testLogger.log(Status.INFO, "Test Status: " + status);
	}

	/*
	 * This will return method names to the calling function
	 */
	private String returnMethodName(ITestNGMethod method) {

		return method.getRealClass().getSimpleName() + "." + method.getMethodName();

	}

	public static ExtentTest getTestLogger() {
		return testLogger;
	}

	public static void setTestLogger(ExtentTest testLogger) {
		Listener.testLogger = testLogger;
	}

}
