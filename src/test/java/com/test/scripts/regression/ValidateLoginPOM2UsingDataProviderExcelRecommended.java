package com.test.scripts.regression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.BaseClass1;
import pages.LoginPage1;

public class ValidateLoginPOM2UsingDataProviderExcelRecommended extends BaseClass1 {
	
	
	@Test(dataProvider="getData")//you need to use parameter to pass the data
	public void loginTest(String un, String pwd, String expTitle)
	{
		LoginPage1 lp1 = PageFactory.initElements(driver, LoginPage1.class);
		
		//lp1.doLogin("reyaz0617", "reyaz123" );
	
		
		//Either above or Use the below where we are calling each method separately
		lp1 = PageFactory.initElements(driver, LoginPage1.class);
		lp1.username(un);
		lp1.password(pwd);
		lp1.login();
		
		
		Assert.assertEquals(driver.getTitle(), expTitle);//No hardcoding
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		FileInputStream fis1 = new FileInputStream("src\\test\\resources\\TestData\\Excels\\LoginData.xlsx");
		XSSFWorkbook wb1 = new XSSFWorkbook(fis1);
		XSSFSheet ws1 = wb1.getSheet("credentials");
		
		int noOfRows = ws1.getPhysicalNumberOfRows()-1; //As first row has heading we use -1 to skip first row
		int noOfCells = ws1.getRow(0).getPhysicalNumberOfCells();
		
		Object [] [] obj = new Object [noOfRows] [noOfCells] ;
		for(int i=0;i<noOfRows; i++)//you can assign value 1 to i=1;i<noOfRows or below way
		{
			for(int j=0; j<noOfCells; j++)
			{

				obj [i][j] = ws1.getRow(i+1).getCell(j).getStringCellValue();
				System.out.print(obj[i][j] + "\t");
			}
			System.out.println();
		}
		
		
		return obj;
		
	}

}
