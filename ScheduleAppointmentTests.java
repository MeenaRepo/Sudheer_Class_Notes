package org.iit.healthcare.mmp.patientmodule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 1. Login
2. Click on Tab ->Schedule Appointment
3. Click on Create NEW Appointment
4. Click on Book Appointment Btn
5. Select Date and Time and continue
6. Enter the symptoms and click on continue.
7. Validate the data is displayed in the home page.


p1: convert into a method
p2: parameterize the method
p3: return the outcome for validation

 *
 */


public class ScheduleAppointmentTests {
	WebDriver driver;
	@Test
	public void validateScheduleAppointment()
	{
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://96.84.175.78/MMP-Release2-Integrated-Build.6.8.000/portal/login.php");
		login("ria1","Ria12345");
		navigateToAModule("Schedule Appointment");
		HashMap<String,String> expHMap = bookAppointment("Smith","Fever");
		HashMap<String,String> actualHMap =fetchPatientPortalData();
		Assert.assertTrue(expHMap.equals(actualHMap));

		
		
	}
	public String login(String uname,String pword)
	{
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(pword);
		driver.findElement(By.name("submit")).click();
		String actual = driver.findElement(By.tagName("h3")).getText();
		return actual;
	}
	public void navigateToAModule(String mName)
	{
		driver.findElement(By.xpath("//span[normalize-space()='"+mName+"']")).click();
	}
	public HashMap<String, String> bookAppointment(String doctorName,String sym)
	{
		HashMap<String,String> appHMap = new HashMap<String,String>();
		driver.findElement(By.xpath("//input[@value='Create new appointment']")).click();
		driver.findElement(By.xpath( "//h4[contains(text(),'Dr."+doctorName+"')]/ancestor::ul/following-sibling::button")).click();
		appHMap.put("doctor",doctorName);
		
		driver.switchTo().frame("myframe");
		driver.findElement(By.id("datepicker")).click();
		
		String dateofAppointment=selectFutureDate(20);
		 
		
		String[] date = dateofAppointment.split("/");
		String appointmentDate= date[0];
		String appointmentMonth = date[1];
		String appointmentYear= date[2];
		
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ui-datepicker-year']")));
		String calYear= driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText(); 
		 
		while(!calYear.equals(appointmentYear)) 
		{
			
			driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
			calYear =driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();
			System.out.println("Printing year::"+ calYear);
		}
		
		String calMonth=driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
		while(!calMonth.equals(appointmentMonth)) 
		{
			
			driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
			calMonth =driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
			System.out.println("Printing Month  ::"+ calMonth);
		}
		driver.findElement(By.linkText(appointmentDate)).click();
		System.out.println("The date selected in the datePicker:" + driver.findElement(By.id("datepicker")).getAttribute("value"));
		appHMap.put("date", driver.findElement(By.id("datepicker")).getAttribute("value"));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("time")));
		new Select(driver.findElement(By.id("time"))).selectByVisibleText("10Am");
		appHMap.put("time","10Am");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='status']")));

		driver.findElement(By.id("ChangeHeatName")).click();

		driver.findElement(By.id("sym")).sendKeys("Fever");
		appHMap.put("sym",driver.findElement(By.id("sym")).getAttribute("value"));
		
		
		driver.findElement(By.xpath("//input[@value='Submit']")).click();
		
		return appHMap;
	}
	public HashMap<String, String> bookAppointmentWithOutCalendarPicker(String doctorName,String sym)
	{
		HashMap<String,String> appHMap = new HashMap<String,String>();
		driver.findElement(By.xpath("//input[@value='Create new appointment']")).click();
		driver.findElement(By.xpath( "//h4[contains(text(),'Dr."+doctorName+"')]/ancestor::ul/following-sibling::button")).click();
		appHMap.put("doctor",doctorName);
		
		driver.switchTo().frame("myframe");
		driver.findElement(By.id("datepicker")).click();
		
		String dateofAppointment=selectFutureDate(20);
		 
		 	 
		driver.findElement(By.id("datepicker")).sendKeys(dateofAppointment);
		System.out.println("The date selected in the datePicker:" + driver.findElement(By.id("datepicker")).getAttribute("value"));
		appHMap.put("date", driver.findElement(By.id("datepicker")).getAttribute("value"));
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("time")));
		new Select(driver.findElement(By.id("time"))).selectByVisibleText("10Am");
		appHMap.put("time","10Am");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='status']")));

		driver.findElement(By.id("ChangeHeatName")).click();

		driver.findElement(By.id("sym")).sendKeys("Fever");
		appHMap.put("sym",driver.findElement(By.id("sym")).getAttribute("value"));
		
		
		driver.findElement(By.xpath("//input[@value='Submit']")).click();
		
		return appHMap;
	}

	public HashMap<String, String> fetchPatientPortalData()
	{
		//table[@class='table']/tbody/tr[1]/td[1]
		//table[@class='table']/tbody/tr[1]/td[2]
		//table[@class='table']/tbody/tr[1]/td[3]
		//table[@class='table']/tbody/tr[1]/td[4]
		HashMap<String,String> patHMap = new HashMap<String,String>();
		patHMap.put("doctor",driver.findElement(By.xpath("//table[@class='table']/tbody/tr[1]/td[4]")).getText());
		patHMap.put("date", driver.findElement(By.xpath("//table[@class='table']/tbody/tr[1]/td[1]")).getText() );
		patHMap.put("time", driver.findElement(By.xpath("//table[@class='table']/tbody/tr[1]/td[2]")).getText());
		patHMap.put("sym",driver.findElement(By.xpath("//table[@class='table']/tbody/tr[1]/td[3]")).getText());
		return patHMap;
	}
	
	public String selectFutureDate(int nofDays)
	{
	      Calendar cal = Calendar.getInstance();
	      System.out.println(cal.getTime());
	      cal.add(Calendar.DAY_OF_MONTH, nofDays);
	      
	      SimpleDateFormat sdf = new SimpleDateFormat("d/MMMMM/YYYY");//10/October/2021
	      String date = sdf.format(cal.getTime());
	      System.out.println(date);
	      return date;
	}

}
