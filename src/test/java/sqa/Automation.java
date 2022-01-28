package sqa;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import capabilities.SetCapabilities;
import variables.Variables;
import variables.Variables;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class Automation extends SetCapabilities {

	ExtentReports extent = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReport.html");
	static ExtentTest test;
	
	@BeforeTest
	public void setUp() throws Exception {
		capabilities();

		extent.attachReporter(spark);
	}

	@AfterTest
	public void teadDown() {
		extent.flush();
		if(driver != null) {
			driver.quit();
		}
	}



	//EMI Calculator option selection

	@Test
	public void selectEMICalculator() throws InterruptedException {
		test = extent.createTest("Select EMI Option");
		WebDriverWait wait = new WebDriverWait(driver,10);
		WebElement selectEMICalculator = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("btnStart")));
		selectEMICalculator.click();
		test.log(Status.PASS, "EMI option selected");
		test.pass("Verified!");
	}

	//Calculate EMI

	@Test
	public void calculateEMI() throws InterruptedException, ParseException {
		
		selectEMICalculator();
		test = extent.createTest("Calculate EMI");
		
		WebDriverWait wait = new WebDriverWait(driver,10);

		WebElement loanAmount = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etLoanAmount")));
		loanAmount.sendKeys(String.valueOf(Variables.loan));
		test.log(Status.PASS, "Input Loan Amount");
		
		WebElement interest = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etInterest")));
		interest.sendKeys(String.valueOf(Variables.EMIinterest));
		test.log(Status.PASS, "Input interest");
		
		WebElement period = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etYears")));
		period.sendKeys(String.valueOf(Variables.timePeriod));
		test.log(Status.PASS, "Input time period");
		
		WebElement processingFee = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etFee")));
		processingFee.sendKeys(String.valueOf(Variables.pFee));
		test.log(Status.PASS, "Input processing Fee");
		
		WebElement calculate = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("btnCalculate")));
		calculate.click();
		test.log(Status.PASS, "Press calculate button");
		
		test.pass("Verified!");
	}

	//Verify Amount

	@Test
	public void verifyCalculation() throws InterruptedException, ParseException {
		
		calculateEMI();
		test = extent.createTest("Verify Amount");
		WebDriverWait wait = new WebDriverWait(driver,10);
		String mEmiResult = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("monthly_emi_result"))).getText();
		String tInterest = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("total_interest_result"))).getText();
		String tProcess = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("processing_fee_result"))).getText();
		String tPayment = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("total_payment_result"))).getText();
		test.log(Status.PASS, "Fetch all the result data");
		
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

		Number number = format.parse(mEmiResult);
		Double mEmi = (double) Math.round(number.doubleValue());
		Assert.assertSame(mEmi, Variables.mEmiResult);
		test.log(Status.PASS, "Emi matched !");
		
		Number number1 = format.parse(tInterest);
		Double totalInterest = (double) Math.round(number1.doubleValue());
		Assert.assertSame(totalInterest, Variables.totalInterest);
		test.log(Status.PASS, "Total Interest matched !");
		
		Number number2 = format.parse(tProcess);
		int totalProceesingFee = (int) Math.round(number2.intValue());
		Assert.assertEquals(totalProceesingFee, Variables.totalProceesingFee);
		test.log(Status.PASS, "Total proceesing fee matched !");
		
		Number number3 = format.parse(tPayment);
		Double totalPayment = (double) Math.round(number3.doubleValue());
		Assert.assertSame(totalPayment, Variables.totalPayment);
		test.log(Status.PASS, "Total payment matched !");
		
		test.pass("Verified!");
	}

}
