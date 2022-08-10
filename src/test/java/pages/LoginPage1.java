package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage1 {
	//Write annotation for each element
	@FindBy(xpath="//input[@name='username']")    //locators 
	WebElement username;
	
	@FindBy(xpath="//input[@name='password']")    //locators 
	WebElement password;
	
	@FindBy(xpath="//input[@name='login']")    //locators 
	WebElement login;
	
	public void username(String text)
	{
		username.sendKeys(text);
	}
	public void password(String text)
	{
		password.sendKeys(text);
	}
	public void login()
	{
		login.click();
	}
	
	public void doLogin(String un, String pwd)//Combined method, however we sometimes need only username etc 
	                                          //so we have above sperate method
	
	
	{
		username.sendKeys(un);
		
		password.sendKeys(pwd);
		login.click();
	}

}
