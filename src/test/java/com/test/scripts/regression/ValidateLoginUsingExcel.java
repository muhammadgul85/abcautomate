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
import org.testng.annotations.Test;

public class ValidateLoginUsingExcel {
	
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
	@Test
	public void logInValid() throws IOException
	{
		FileInputStream fis2 = new FileInputStream("src\\test\\resources\\TestData\\Excels\\LoginData.xlsx"); 
		XSSFWorkbook wb = new XSSFWorkbook(fis2);
		XSSFSheet ws = wb.getSheet("credentials");
		
		Iterator<Row> rows = ws.rowIterator();
		
		Row row = null;
		
		//This is to escape the heading row as it doesn't have our required data 
		rows.next(); 
		
		while(rows.hasNext())
		{
			row = rows.next();
		// Data Parameterization, Different set of test data for same test cases, we will use data provider concept in testNG which will start fresh browser everytime
			//Data Provider is the best way rather than clearing the username and password
			
			driver.findElement(By.xpath("//input[@name='username']")).clear();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(row.getCell(0).getStringCellValue());
		
		driver.findElement(By.xpath("//input[@name='password']")).clear();
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(row.getCell(1).getStringCellValue());
		
		driver.findElement(By.xpath("//input[@name='login']")).click();
		
		Assert.assertEquals(driver.getTitle(), row.getCell(2).getStringCellValue());
		
		driver.navigate().back();// issue is the username and password aren't cleared when you come back		
								//We will have to clear them everytime
								// We need to use clear() method for username and password to clear everytime loop runs through
		
		}
		
	}


}
