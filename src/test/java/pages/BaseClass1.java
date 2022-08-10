package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseClass1 { 
	protected static WebDriver driver;
	FileInputStream fis1; 
	Properties pr1; 
	
	@BeforeClass
	public void readFile() throws IOException
	{
		fis1 = new FileInputStream("Properties\\Config.properties");
		pr1 = new Properties();
		pr1.load(fis1);		
	}
	@BeforeMethod
	public void setUp()
	{
		String browsername = pr1.getProperty("browser");
		if (browsername.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			driver = new ChromeDriver();			
		}
		if(browsername.equalsIgnoreCase("FireFox"))
		{
			System.setProperty("webdriver.chrome.driver", "Drivers\\geckodriver.exe");
			driver= new FirefoxDriver();
			
		}
		driver.get(pr1.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Long.parseLong(pr1.getProperty("implicitWait")), TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}
