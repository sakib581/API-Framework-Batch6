package com.woa.core;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;


public class TestBase {
    private static ExtentReports extent;

    //Extent Report Setup
    @BeforeSuite(alwaysRun = true)
    public void extentSetup(ITestContext context) {
        ExtentTestManager.setOutputDirectory(context);
        extent = ExtentTestManager.getInstance();
    }

    //Extent Report Setup for each methods
    @BeforeMethod(alwaysRun = true)
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    //Extent Report cleanup for each methods
    @AfterMethod(alwaysRun = true)
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(ExtentTestManager.getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(ExtentTestManager.getTime(result.getEndMillis()));
        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "TEST CASE PASSED : " + result.getName());
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, "TEST CASE FAILED : " + result.getName() + " :: " + ExtentTestManager.getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "TEST CASE SKIPPED : " + result.getName());
        }
        ExtentTestManager.endTest();
        extent.flush();
    }

    //Extent Report closed
    @AfterSuite(alwaysRun = true)
    public void generateReport() {
        extent.close();
    }


    
}
