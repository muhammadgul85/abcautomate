package com.test.scripts.regression;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import keywords.LoginKeyWords;

//We will use data provider not hardcoded data

public class ValidateLoginUsingHybrid {
	
	FileInputStream fis1 ;
	XSSFWorkbook wb1;
	XSSFSheet ws1; 
	
	FileInputStream fis2; 
	Properties pr2; 
	
	FileInputStream fis3; 
	Properties pr3;
	
	LoginKeyWords keys; //We need to use browser method which is in another class called LoginKeywords 
						//Therefore we imported it and created reference, we will initialise loginkeywords in BeforeClass startUp() Method
	@BeforeClass
	public void startUp () throws IOException
	{
		fis1 = new FileInputStream("src\\test\\resources\\TestData\\Excels\\LoginKeywords.xlsx");
		wb1 = new XSSFWorkbook(fis1);
		ws1 = wb1.getSheet("Sheet1");
		
		
		fis2 = new FileInputStream("Properties\\Config.properties");
		pr2 = new Properties();
		pr2.load(fis2);
		
		

		fis3 = new FileInputStream("Properties\\locator.properties");
		pr3 = new Properties();
		pr3.load(fis3);
		
		keys = new LoginKeyWords();//initialised the LoginKeyWords
	}
	
	@Test(dataProvider="getData")
	//We have 3 parameters i.e. username, password and expTitle
	public void validateLoginTest(String un, String pwd, String expTitle)
	{
		Iterator<Row> rows = ws1.rowIterator();
		rows.next(); // to skip first line in excel sheet
		
		Row row =null;
		
		while(rows.hasNext())
		{
			row = rows.next();
			//4th cell in each row which contain keyword
			
			String action = row.getCell(4).getStringCellValue();
			
			if(action.equalsIgnoreCase("startBrowser"))
			{
				keys.startBrowser(pr2.getProperty("browser"));
			}
			if(action.equalsIgnoreCase("launchApp"))
			{
				keys.launchApp(pr2.getProperty("url"), pr2.getProperty("implicitWait"));
			}
			
			if(action.equalsIgnoreCase("enterUserName"))
			{
				keys.enterUserName(pr3.getProperty("username_text"), un);//we are hard coding data here as it's keyword driven not data driven 
				
				
			}
			
			if(action.equalsIgnoreCase("enterPassword"))
			{
				keys.enterPassword(pr3.getProperty("passwrod_text"), pwd);//we are hard coding data here as it's keyword driven not data driven 
				
				
			}
			if(action.equalsIgnoreCase("clickButton"))
			{
				keys.clickButton(pr3.getProperty("login_btn"));
				
				
			}
			
			if(action.equalsIgnoreCase("verifyTitle"))
			{
				keys.verifyTitle(expTitle);
			}
			
			if(action.equalsIgnoreCase("quitBrowser"))
			{
				keys.quitBrowser();
			}
			
			
		}
		
		
	}
	@DataProvider
	public Object [] [] getData () throws IOException
	{
		
		
		FileInputStream fis = new FileInputStream("src\\test\\resources\\TestData\\Excels\\LoginData.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook (fis);
		XSSFSheet ws = wb.getSheet("credentials");
		
		
		
		int noOfRows = ws.getPhysicalNumberOfRows()-1; //We are using -1 to skip first row as it contains title etc not the test data
		int noOfCells = ws.getRow(0).getPhysicalNumberOfCells();//Get the no of cells
		
		
		Object data [][] = new Object[noOfRows][noOfCells]; //We are passing noOfRows and noOfCells because we are not using row, isntead we are passing 3 paramters per each set
		//use for loop to iterate over
		
		for(int i=0; i<noOfRows; i++)
		{
			for(int j=0; j<noOfCells; j++)
			{
			data[i][j] = ws.getRow(i+1).getCell(j).getStringCellValue() ;
		}
		}
		
		
		
		return data;// We will return data object
		
	}
}

