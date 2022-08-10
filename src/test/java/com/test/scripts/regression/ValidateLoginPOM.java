package com.test.scripts.regression;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.BaseTest;
import pages.LoginPage;

public class ValidateLoginPOM extends BaseTest {
	
	
	@Test
	public void validateLoginTest()
	{
		LoginPage lp = PageFactory.initElements(driver, LoginPage.class);
		lp.username("reyaz0617");
		lp.password("reyaz123");
		lp.login();
		//
		
		Assert.assertEquals(driver.getTitle(), "Adactin.com - Search Hotel");
	}
	
	

}
