package pages;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {
//Parent class of all tests 
	protected static WebDriver driver; // make it protected or public static to make it common across
	//public static extent reports and common names e.g. reports and test
	public static ExtentReports reports;
	public static ExtentTest test;
	FileInputStream fis1; 
	Properties pr1;
	
	
	@BeforeTest
	public void setUpReports()
	{
		//Below we are creating new folder Reports, if already exists boolean will show as false
		String reportDirPath = System.getProperty("user.dir")+ "\\Reports";
		//For directory/folder also there is file object, we create folder/dir
		
		File reportDir = new File(reportDirPath);
		
		//Create new file method mkdir, below is boolean,   
		
		reportDir.mkdir();
		//define date pattern 
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
		
		date=date.replace(":" , "-");
		System.out.println(date);
		
		//Create an HTML File in the directory or folder, so we can open in browser	
		File reportFile = new File(reportDirPath+ "\\"+date+".html");//File object
		
		//we have not declared it as instance variable as we need it only locally
		reports = new ExtentReports();
		test = reports.createTest("Regression Results");//to create reports 
		
		ExtentSparkReporter extent= new ExtentSparkReporter(reportFile);
		extent.config().setDocumentTitle("Results of Regression");
		//attach the report
		reports.attachReporter(extent);
	}
	@AfterTest
	
	public void closeReports()
	{
		reports.flush(); //reporting is done by this flush method, as we close report by this
	}
	
	
	
	
	@BeforeClass
	public void readFiles() throws IOException
	{
		fis1= new FileInputStream("Properties\\Config.properties");
		pr1 = new Properties();
		pr1.load(fis1);		
	}
	
	@BeforeMethod 
	
	public void setUp()
	{
		String browserName = pr1.getProperty("browser");
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		
		else if (browserName.equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver= new FirefoxDriver();
			
		}
		driver.get(pr1.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Long.parseLong(pr1.getProperty("implicitWait")), TimeUnit.SECONDS);
		
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
}
