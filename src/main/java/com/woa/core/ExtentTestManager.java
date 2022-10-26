package com.woa.core;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.Reporter;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ExtentTestManager {

    public static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    public static ExtentReports extent;
    public static ITestContext context;

    //logging to report
    public static void log(String message, Class classs) {
        Logger logger = Logger.getLogger(String.valueOf(classs));
        logger.info(message + " : " + classs);
        Reporter.log(message + " : " + classs, true);
        ExtentTestManager.getTest().log(LogStatus.INFO, message + " : " + classs + "<br>");
        //System.out.println("");
    }

    //logging to report
    public static void log(String message) {
        Reporter.log(message + "<br>", true);
        ExtentTestManager.getTest().log(LogStatus.INFO, message + "<br>");
    }

    protected static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    protected static synchronized void endTest() {
        extent.endTest(extentTestMap.get((int) Thread.currentThread().getId()));
    }

    protected static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    protected static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    protected synchronized static ExtentReports getInstance() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(cal.getTime());

        if (extent == null) {
            File outputDirectory = new File(context.getOutputDirectory());
            File resultDirectory = new File(outputDirectory.getParentFile(), "html");
            extent = new ExtentReports(System.getProperty("user.dir") + "/Execution_Report/Report_html" + date + ".html", true);
            Reporter.log("Extent Report Directory" + resultDirectory, true);
            extent.addSystemInfo("Host Name", "").addSystemInfo("Environment",
                    "QA").addSystemInfo("User Name", "");
            extent.loadConfig(new File(System.getProperty("user.dir") + "/src/main/resources/report-config.xml"));
        }
        return extent;
    }

    protected static void setOutputDirectory(ITestContext context) {
        ExtentTestManager.context = context;
    }

    //get execution time
    protected static Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    //print stacktrace
    protected static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}