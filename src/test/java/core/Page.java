package core;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection; // JDBC
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class Page 
{
	 public WebDriver driver = null; // global variable
	 public Connection con = null;  
	 public Logger log = null;  
	 public ExtentReports report = null;
	 public ExtentTest test = null;
	 public ATUTestRecorder recorder = null;
	 
	public void openBrowser(String browser,String url)   // define
	{
		if(browser.equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("edge"))
		{
			driver = new EdgeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		
	//	driver.get(url); // not prefer cannot go back / forward / refresh
	
		     // or.....
		driver.navigate().to(url); // prefer can go back / forward / refrerh
		
		PageFactory.initElements(driver, this); // for @FindBy
				
		// implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		
		driver.manage().window().maximize(); // full screen of laptop
	}
	
	public void closeBrowser()
	{
		driver.quit();
	}
	
	public void makeWBConnection(String wbpath) throws Exception
	{
		Class.forName("com.googlecode.sqlsheet.Driver"); // register driver
	    con = DriverManager.getConnection("jdbc:xls:file:"+wbpath);
	System.out.println("Connected..");
	}

	public void closeWBConnection() throws Exception
	{
		con.commit();
		con.close();
	}
	
	public void takeScreenShot(String filename) throws Exception
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); // store file to temporary location
		//Now you can do whatever you need to do with it, for example copy somewhere download org.apache.commons.io.FileUtils class API set classpath and use this class to copy.
		String screenshotpath = System.getProperty("user.dir")+"\\src\\test\\java\\srcshot\\"+filename+".jpeg";
		
		FileUtils.copyFile(scrFile, new File(screenshotpath));

	}
	
	public void makeLog(String filename) throws Exception
	{
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+filename+".properties");
	
		p.load(fis);
		p.put("log4j.appender.dest1.File", System.getProperty("user.dir")+"//src//test//java//logs//aa.log");
		PropertyConfigurator.configure(p);
		
	log =	Logger.getLogger(filename);
	}
	
	public void makeReport(String filename) throws Exception
	{
		report = new ExtentReports(System.getProperty("user.dir")+"//src//test//java//reports//"+filename+".html");
	    test = report.startTest(filename);
	}
	
	public void closeReport() throws Exception
	{
		report.endTest(test);
		report.flush();
	}
	
	public void startVideo(String filename) throws Exception
	{
		//DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		//Date date = new Date();
		recorder = new ATUTestRecorder(System.getProperty("user.dir")+"//src//test//java//video",filename,false); // "RecordedVideo-"+dateFormat.format(date)
		recorder.start();
		
	}
	
	public void stopVideo() throws Exception
	{
		recorder.stop();
	}
}
