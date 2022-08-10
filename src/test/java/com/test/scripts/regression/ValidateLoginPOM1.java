package com.test.scripts.regression;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.BaseClass1;
import pages.LoginPage1;

public class ValidateLoginPOM1 extends BaseClass1 {
	
	@Test
	public void loginTest()
	{
		LoginPage1 lp1 = PageFactory.initElements(driver, LoginPage1.class);
		
		lp1.doLogin("reyaz0617", "reyaz123" );
	
		
		//Either above or Use the below where we are calling each method separately
		/*LoginPage1 lp1 = PageFactory.initElements(driver, LoginPage1.class);
		lp1.username("reyaz0617");
		lp1.password("reyaz123");
		lp1.login();
		*/
		
		Assert.assertEquals(driver.getTitle(), "Adactin.com - Search Hotel");
	}

}
