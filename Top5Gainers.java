package org.iit.healthcare.selassingments;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Top5Gainers {
	WebDriver driver ;
	@Test
	public void validateData()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://money.rediff.com/gainers");
		boolean result = displayListOfGainers(10,"Raj Oil Mills Ltd.");
		Assert.assertTrue(result);
	}
	public boolean displayListOfGainers(int nRows,String stockName)
	{
		List<WebElement> trList = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr"));
		boolean result = false;
		for(int i=0;i<trList.size();i++)
		{
			if(i==nRows)
			{
				break;
			}
			System.out.println("Table row contents:::  " + trList.get(i).getText());
			if(trList.get(i).getText().contains(stockName))
			{
				result= true;
			}

		}
		return result;
	}

}
//ctrl+shift+o- import packages