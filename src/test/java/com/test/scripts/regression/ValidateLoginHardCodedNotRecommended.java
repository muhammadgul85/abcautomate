package com.test.scripts.regression;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ValidateLoginHardCodedNotRecommended {
	
	
	WebDriver driver; 
	
	
	@BeforeMethod 
	public void setUp() throws IOException
	{
		FileInputStream fis = new FileInputStream("Properties\\Config.properties");
		Properties pr1 = new Properties();
		
		pr1.load(fis);
		
		String browserName = pr1.getProperty("browser");
		
		if (browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			
			
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			
		}
		
		driver.get(pr1.getProperty("url"));      //30 is string value in Config file and we want to convert to Long so we use Long.parseLong
		driver.manage().timeouts().implicitlyWait(Long.parseLong(pr1.getProperty("implicitWait")), TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@AfterMethod
	public void tearDown() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.quit();
	}
	
	@Test
	public void validLoginTest()
	{
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("reyaz0617");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("reyaz123");
		driver.findElement(By.xpath("//input[@name='login']")).click();
		
		Assert.assertEquals(driver.getTitle(), "Adactin.com - Search Hotel");
	}
	
	
	
	
	

}
