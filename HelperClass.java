package org.iit.healthcare.mmp;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperClass {

WebDriver driver;
	
	public HelperClass(WebDriver driver){
		this.driver = driver;
	}
	
	public void navigateToAModule(String mName)
	{
		driver.findElement(By.xpath("//span[normalize-space()='"+mName+"']")).click();
	}
	
	public  void launchApplicationURL(String url)	{
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String login(String uname,String pword)
	{
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(pword);
		driver.findElement(By.name("submit")).click();
		String actual = driver.findElement(By.tagName("h3")).getText();
		return actual;
	}
 
	public void captureScreenshot(String tc_Name) throws IOException
	{
		System.out.println("Inside Capturing Screenshot method");
		TakesScreenshot tsh = (TakesScreenshot)driver;
		File sourceFile = tsh.getScreenshotAs(OutputType.FILE);
		System.out.println(sourceFile.getAbsolutePath());
		String destinationPath = System.getProperty("user.dir")+"\\screenshots\\"+tc_Name+"_"+
											Calendar.getInstance().getTimeInMillis()%1000000000+".jpg";
		File destFile = new File(destinationPath); 
		FileUtils.copyFile(sourceFile,destFile);
		System.out.println(destinationPath);
		System.out.println("Exiting Screenshot");
		
	}
	public WebDriver switchToAFrameAvailable(String frameId,int timeinSecs)
	{
		WebDriverWait wait = new WebDriverWait(driver,timeinSecs);
		driver = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
		return driver;
	}
	public WebElement visibilityofElementLocated(By locator,int timeinSecs)
	{
		WebDriverWait wait = new WebDriverWait(driver,timeinSecs);
		WebElement e= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return e;
	}
	public void switchToSideBar(){
		driver.findElement(By.xpath("//div[@class='left-sidebar']")).click();
	}
	public void highLightElement(WebElement ele){
		JavascriptExecutor js =(JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style', 'background:yellow; border:2px solid red;')", ele);
	}
	//write symptoms
		public void inputSymptoms(String symptom) {
			driver.findElement(By.xpath("//textarea[@name='sym']")).clear();
			driver.findElement(By.xpath("//textarea[@name='sym']")).sendKeys(symptom);
			driver.findElement(By.xpath("//form[@name='symptoms']/descendant::div/input[@value='Submit']")).click();
		}

		/**
		 * Added the conditional statements to check with SSN to get the correct patient Name
		 * @param pName
		 * @param SSN
		 * @throws InterruptedException
		 */
		public void searchPatient(String pName, String SSN) throws InterruptedException {

			driver.findElement(	By.id("search")).sendKeys(pName);
			//driver.findElement(By.className("tfbutton")).click();
			driver.findElement(By.xpath("//input[@value='search']")).click();
			System.out.println(pName);
			System.out.println(SSN);
			Thread.sleep(3000);
			List<WebElement> pLis = driver.findElements(By.xpath("//div[@id='show']/descendant::table/tbody/tr/td/a"));
			//List <WebElement> pLis = driver.findElements(By.xpath("//div[@id='show']/table/tbody/tr/td[1]/a"));
			for(int i=1;i<pLis.size();i++){ 
				System.out.println(i);
				//String s1 = driver.findElement(By.xpath("//div[@id='show']/table/tbody/tr["+i+"]/td[2]")).getText();
				String s1 = driver.findElement(By.xpath("//div[@id='show']/descendant::table/tbody/tr["+i+"]/td[2]")).getText();
				//System.out.println(s1);
				if((pLis.get(i).getText().equals(pName)) && (s1.equals(SSN))){
					System.out.println("Inside if condition");
					System.out.println(pLis.get(i).getText());
					System.out.println(s1);
					pLis.get(i-1).click();
					break;
				}
			}	
		}
	 
		public void navigateToPatientServices(String serviceName){
			WebElement we = driver.findElement(By.xpath("//input[@value='"+serviceName+"']"));
			scrollIntoViewOfWebElement(we);
			we.click();
			System.out.println("Clicking the given ServiceName "+serviceName);
		}
		public void scrollIntoViewOfWebElement(WebElement we){
			JavascriptExecutor js = ((JavascriptExecutor)driver);
			js.executeScript("arguments[0].scrollIntoView(true);",we);
			System.out.println("Scrolling down to the exact location" );
		}
	    public void closeDriver() {
		           driver.close();
		}
}
