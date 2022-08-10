package com.test.scripts.regression;

import java.io.FileInputStream;

import java.io.IOException;

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

public class DataProviderUsingExcelBestWay {//as we will not use hardcoded value in real life
	String pageTitle;
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
		driver.findElement(By.xpath("//a[text()='New User Register Here']")).click();
		System.out.println("Pagetitle: "+driver.getTitle());
	
		 pageTitle = driver.getTitle();
		driver.manage().window().maximize();
		
		
		
	}
	
	@AfterMethod
	public void tearDown() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.quit();
	}
	
	@DataProvider
	public Object [] [] getData () throws IOException
	{
		
		
		FileInputStream fis = new FileInputStream("src\\test\\resources\\TestData\\Excels\\RegistrationData.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook (fis);
		XSSFSheet ws = wb.getSheet("credentials");
		
		
		
		int noOfRows = ws.getPhysicalNumberOfRows()-1; //We are using -1 to skip first row as it contains title etc not the test data
		int noOfCells = ws.getRow(0).getPhysicalNumberOfCells();//Get the no of cells
		
		
		Object data [][] = new Object[noOfRows][1]; //Object the mother of all classes//\no of cells will be 1, as for each test data we have taken the whole row as data
		
		//use for loop to iterate over
		
		for(int i=0; i<noOfRows; i++)
		{
			data[i][0] = ws.getRow(i+1); 
		}
		
		
		
		return data;// We will return data object
		
	}
	
	
	
	@Test(dataProvider="getData") //We are passing methodname that is providing data
	public void logInValid(Row row) throws IOException
	{
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(row.getCell(0).getStringCellValue());
		
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(row.getCell(1).getStringCellValue());
		
		driver.findElement(By.xpath("//input[@id='re_password']")).sendKeys(row.getCell(2).getStringCellValue());
		driver.findElement(By.xpath("//input[@id='full_name']")).sendKeys(row.getCell(3).getStringCellValue());
		driver.findElement(By.xpath("//input[@id='email_add']")).sendKeys(row.getCell(4).getStringCellValue());
		driver.findElement(By.xpath("//input[@id='captcha-form']")).sendKeys(row.getCell(5).getStringCellValue());
		
		driver.findElement(By.xpath("//input[@id='tnc_box']")).click();
		
		driver.findElement(By.xpath("//input[@id='Submit']")).click();
		
		Assert.assertEquals(pageTitle, row.getCell(6).getStringCellValue());//make pageTitle as instance variable
		
		
		
		
		}
	}


