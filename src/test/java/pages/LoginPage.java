package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {


	@FindBy (xpath="//input[@name='username']")

	WebElement username; //username is variable

	@FindBy (xpath="//input[@name='password']")

	WebElement password; 

	@FindBy (xpath="//input[@name='login']")

	WebElement login; 
	
	public void username(String text)//username is method, so don't get confused
	{
		username.sendKeys(text);
	}
	public void password(String text)//password is method, so don't get confused
	{
		password.sendKeys(text);
		
	}
	public void login()//
	{
		login.click();
		
	}
	
	//We can use a collective method as given below but the above separete method are recommended as sometimes we need to test them individually for example we only have to provide username in test and click on login to see error message
	public void doLogin(String un, String pwd)
	{
		username.sendKeys(un);
		password.sendKeys(pwd);
		login.click();
	}
	
}
