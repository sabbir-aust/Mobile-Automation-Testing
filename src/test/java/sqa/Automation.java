package sqa;

import java.net.URL;
import java.text.DecimalFormat;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class Automation {

	public static final String APPIUM = "http://localhost:4723/wd/hub";
	
	private AndroidDriver driver;
	
	@BeforeTest
	public void setUp() throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "9");
		caps.setCapability("deviceName", "Sabbir");
		caps.setCapability("udid", "emulator-5554");
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("appPackage", "com.continuum.emi.calculator");
		caps.setCapability("appActivity", "com.finance.emicalci.activity.Splash_screnn");

		driver = new AndroidDriver(new URL(APPIUM),caps);
		
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
		WebElement selectEMICalculator = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.continuum.emi.calculator:id/btnStart")));
		selectEMICalculator.click();
		Thread.sleep(5000);

	}
	
	//Calculate EMI
	
	@Test
	public void calculateEMI() throws InterruptedException {
	
		selectEMICalculator();
		
		double loan = 325000;
		double EMIinterest = 9.5;
		int timePeriod = 5;
		double pFee = 1.5;
		
		WebDriverWait wait = new WebDriverWait(driver,10);
		
		WebElement loanAmount = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etLoanAmount")));
		loanAmount.sendKeys(String.valueOf(loan));
		
		WebElement interest = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etInterest")));
		interest.sendKeys(String.valueOf(EMIinterest));
		
		WebElement period = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etYears")));
		period.sendKeys(String.valueOf(timePeriod));
		
		WebElement processingFee = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("etFee")));
		processingFee.sendKeys(String.valueOf(pFee));
		
		WebElement calculate = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("btnCalculate")));
		calculate.click();
		
		Thread.sleep(5000);
		
		WebElement mEMI = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("monthly_emi_result")));
		WebElement tInterest = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("total_interest_result")));
		WebElement tpFee = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("processing_fee_result")));
		WebElement tPayment = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("total_payment_result")));
		
		System.out.println("EMI from Automation: ");
		System.out.println(mEMI.getText());
		System.out.println(tInterest.getText());
		System.out.println(tpFee.getText());
		System.out.println(tPayment.getText());
		
		//Verify Amount
		System.out.println("\nEMI verify: ");
		EMIinterest = EMIinterest/(12*100);
		timePeriod = timePeriod*12;
		
		double emi= (loan*EMIinterest*Math.pow(1+EMIinterest,timePeriod))/(Math.pow(1+EMIinterest,timePeriod)-1);
		System.out.println(new DecimalFormat("##.#").format(emi));
		
		double totalInterest= (emi-(loan/timePeriod))*timePeriod;
		System.out.println(new DecimalFormat("##.#").format(totalInterest));
		
		double totalProceesingFee = loan*(pFee/100);
		System.out.println(new DecimalFormat("##.#").format(totalProceesingFee));
		
		double totalPayment = emi*timePeriod;
		System.out.println(new DecimalFormat("##.#").format(totalPayment));

	}
	
}
