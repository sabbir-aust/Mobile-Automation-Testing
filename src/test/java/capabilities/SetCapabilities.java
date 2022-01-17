package capabilities;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class SetCapabilities {
	public static AndroidDriver driver;
	public static final String APPIUM = "http://localhost:4723/wd/hub";
	public static final String APP = "F:\\Brain-Station-23\\apk\\emi-calculator.apk";
	
	public static void capabilities() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "9");
		caps.setCapability("deviceName", "Sabbir");
		caps.setCapability("udid", "emulator-5554");
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("app",APP );

		driver = new AndroidDriver(new URL(APPIUM),caps);
	}
}
