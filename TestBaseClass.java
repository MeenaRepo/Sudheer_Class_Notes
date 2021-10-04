package org.iit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBaseClass {

	protected WebDriver driver;
	protected Properties prop = new Properties();
	
	
	@BeforeTest
	@Parameters({"path"})
	public void loadProperties(String path) throws IOException
	{
		String absolutePath = System.getProperty("user.dir")+path;
		File f = new File(absolutePath);
		FileInputStream fis = new FileInputStream(f);
		prop.load(fis);
		
	}
	@BeforeClass
	public void instantiateDriver() throws IOException
	{
		System.out.println("First line of the instantiate method");
		 
		String browser = prop.getProperty("browser");
		if(browser.equalsIgnoreCase("chrome")){
			System.out.println("Inside if");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("firefox")){	
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		}
		driver.manage().window().maximize();
		System.out.println("LastLine of the instatiate method of TestBase");
		 
	 
	}
	 
}
