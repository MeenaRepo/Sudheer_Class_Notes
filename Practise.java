package org.iit.healthcare.mmp.patientmodule;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Practise {
	
	WebDriver driver;
	public void validateScheduleAppointment()
	{
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		String url="http://96.84.175.78/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";
		String actual = launchBrowser(url);
		String expected="";
		Assert.assertEquals(actual,expected);
		
	}
	public String launchBrowser(String url)
	{
		 
		driver.get(url);
		String title = driver.getTitle();
		return title;
	}
}
