package com.test.scripts.regression;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderUsingDataProviderNotRecommendedway {//as we will not use hardcoded value in real life
	
	WebDriver driver; 
	@BeforeMethod
	public void setUp() throws IOException
	{
		FileInputStream fis = new FileInputStream("Properties\\Config.properties");
	//  FileInputStream fis = new FileInputStream("Properties\\Config.properties");
		
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
			driver= new FirefoxDriver();
			
		}
		driver.get(pr1.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Long.parseLong(pr1.getProperty("implicitWait")), TimeUnit.SECONDS);
		
	
		driver.manage().window().maximize();
		
		
		
	}
	
	@AfterMethod
	public void tearDown() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.quit();
	}
	
	@DataProvider
	public Object [] [] getData ()
	{
		Object data [][] = new Object[4][3]; //Object the mother of all classes
		
		data[0][0] = "reyaz0617";
		data[0][1] = "reyaz123";
		data[0][2] = "Adactin.com - Search Hotel";
		
		data[1][0] = "reyaz0617";
		data[1][1] = "reyaz456";
		data[1][2] = "Adactin.com - Hotel Reservation System";
		
		data[2][0] = "reyaz1212";
		data[2][1] = "reyaz123";
		data[2][2] = "Adactin.com - Hotel Reservation System";
		
		data[3][0] = "reyaz1212";
		data[3][1] = "reyaz123";
		data[3][2] = "Adactin.com - Hotel Reservation System";
		
		
		return data;// We will return data object
		
	}
	
	
	
	@Test(dataProvider="getData") //We are passing methodname that is providing data
	public void logInValid(String username, String password, String expTitle) throws IOException
	{
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
		
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		
		driver.findElement(By.xpath("//input[@name='login']")).click();
		
		Assert.assertEquals(driver.getTitle(), expTitle);
		
		
		
		}
	}


