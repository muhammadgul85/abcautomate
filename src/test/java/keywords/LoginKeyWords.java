package keywords;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class LoginKeyWords {
	
	WebDriver driver ;
	
	public void startBrowser (String browserName)
	{
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			
			driver = new ChromeDriver();
			
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver= new FirefoxDriver();
		}
	}
	
	public void launchApp(String url, String implicitWait )
	{
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Long.parseLong(implicitWait), TimeUnit.SECONDS);
	}
	
	public void enterUserName(String xpath, String text)// xpath will be stored in properties file

	{
		driver.findElement(By.xpath(xpath)).sendKeys(text);
		
	}
	
	public void enterPassword(String xpath, String text)
	{
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}
	
	public void clickButton(String xpath)
	{
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public void verifyTitle(String expTitle)
	{
		Assert.assertEquals(driver.getTitle(), expTitle);
	}
	
	public void quitBrowser()
	{
		
		driver.quit();
	}

}
