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

import capabilities.SetCapabilities;
import variables.Variables;
import variables.Variables;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class Automation extends SetCapabilities {

	@BeforeTest
	public void setUp() throws Exception {
		capabilities();

	}

	@AfterTest
	public void teadDown() {
		if(driver != null) {
			driver.quit();
		}
	}


	//EMI Calculator option selection

	@Test
	public void selectEMICalculator() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,10);
		WebElement selectEMICalculator = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("btnStart")));
		selectEMICalculator.click();
	}

	//Calculate EMI

	@Test
	public void calculateEMI() throws InterruptedException, ParseException {

		selectEMICalculator();

		WebDriverWait wait = new WebDriverWait(driver,10);

		WebElement loanAmount = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etLoanAmount")));
		loanAmount.sendKeys(String.valueOf(Variables.loan));

		WebElement interest = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etInterest")));
		interest.sendKeys(String.valueOf(Variables.EMIinterest));

		WebElement period = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etYears")));
		period.sendKeys(String.valueOf(Variables.timePeriod));

		WebElement processingFee = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etFee")));
		processingFee.sendKeys(String.valueOf(Variables.pFee));

		WebElement calculate = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("btnCalculate")));
		calculate.click();

	}

	//Verify Amount

	@Test
	public void verifyCalculation() throws InterruptedException, ParseException {
		calculateEMI();

		WebDriverWait wait = new WebDriverWait(driver,10);
		String mEmiResult = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("monthly_emi_result"))).getText();
		String tInterest = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("total_interest_result"))).getText();
		String tProcess = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("processing_fee_result"))).getText();
		String tPayment = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("total_payment_result"))).getText();

		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

		Number number = format.parse(mEmiResult);
		Double mEmi = (double) Math.round(number.doubleValue());
		Assert.assertSame(mEmi, Variables.mEmiResult);

		Number number1 = format.parse(tInterest);
		Double totalInterest = (double) Math.round(number1.doubleValue());
		Assert.assertSame(totalInterest, Variables.totalInterest);

		Number number2 = format.parse(tProcess);
		int totalProceesingFee = (int) Math.round(number2.intValue());
		Assert.assertEquals(totalProceesingFee, Variables.totalProceesingFee);

		Number number3 = format.parse(tPayment);
		Double totalPayment = (double) Math.round(number3.doubleValue());
		Assert.assertSame(totalPayment, Variables.totalPayment);

	}

}
