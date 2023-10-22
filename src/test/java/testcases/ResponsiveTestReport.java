package testcases;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;

public class ResponsiveTestReport extends Page
{
    // global
	@FindBy(xpath = xpath.AllXpath.imgxpath)
	WebElement img;
	
	public void test() //throws Exception
	{  
		     try {
		    	 makeReport("reports");
		    	 test.log(LogStatus.INFO,"Responsive Test Report called...");
		makeLog("responsivelog");
		//makeWBConnection("C:\\core\\selenium\\resoponsive1.xlsx"); // open connection with WB
	        // or
		makeWBConnection(System.getProperty("user.dir")+"\\src\\test\\java\\excel\\book21.xlsx");
		Statement stm =	con.createStatement();
	ResultSet rs = stm.executeQuery("select * from Sheet1");
	test.log(LogStatus.PASS,"Connected to WB,Getting Data to test");
	while(rs.next())
	{
		// read data from excel
		String browser = rs.getString(1);
		String url = rs.getString(2);
		String resolution = rs.getString(3);
		
		log.debug("opening "+browser+" url "+url+" resolution "+resolution);
		test.log(LogStatus.PASS,"opening "+browser+" url "+url+" resolution "+resolution);
		// break resolution..
		StringTokenizer str = new StringTokenizer(resolution,",");
		int w = Integer.parseInt(str.nextToken().trim());
		int h = Integer.parseInt(str.nextToken().trim());
		
		String expwidth = rs.getString(4);  // expected data
		String expheight = rs.getString(5);  // expected data
		log.debug("expw "+expwidth+" exph "+expheight);
		test.log(LogStatus.INFO,"expw "+expwidth+" exph "+expheight);
		openBrowser(browser,url); // inherited from Page
		driver.manage().window().setSize(new Dimension(w,h));
		test.log(LogStatus.PASS,"Browser opened with resolution"+ resolution);
		takeScreenShot("img-"+resolution); // inherited from Page to take screen shot
		
		  // get data from website
	//	WebElement img = driver.findElement(By.xpath("//*[@id=\"band\"]/div/div[1]/img"));
	          // or...
		String actualwidth = img.getCssValue("width");
		String actualheight = img.getCssValue("height");
		
		log.debug("actualw "+actualwidth+" actualh "+actualheight);
		test.log(LogStatus.INFO,"actualw "+actualwidth+"actualh "+actualheight);
		 if(expwidth.equals(actualwidth) && expheight.equals(actualheight))
		 {
			 // pass
	stm.executeUpdate("insert into Sheet2(resolution,expected,actual,status) values('"+resolution+"','"+expwidth+" x "+expheight+"','"+actualwidth+" x "+actualheight+"','Pass')");		 
		closeWBConnection(); // compulsory to commit and close connection
		 
		// reopen connection
		//makeWBConnection("C:\\core\\selenium\\responsive1.xlsx"); // open connection with WB
		      // or...
		makeWBConnection(System.getProperty("user.dir")+"\\src\\test\\java\\excel\\book21.xlsx");
		stm =	con.createStatement();
		log.debug("pass");
		test.log(LogStatus.PASS,"Matched");
	 
		 }
		 else
		 {
			 // fail
	stm.executeUpdate("insert into Sheet2(resolution,expected,actual,status) values('"+resolution+"','"+expwidth+" x "+expheight+"','"+actualwidth+" x "+actualheight+"','Fail')");		 
		closeWBConnection(); // compulsory to commit and close connection
				 
		// reopen connection
		//makeWBConnection("C:\\core\\selenium\\responsive1.xlsx"); // open connection with WB
		      // or..
		
		makeWBConnection(System.getProperty("user.dir")+"\\src\\test\\java\\excel\\book21.xlsx");
		
		stm =	con.createStatement();
		log.debug("fail");
		test.log(LogStatus.FAIL,"Not Match");
			
		 }
	}  // end of while
		     } // end of try
	         catch(Exception e)
		     {
	        	 log.debug(e.getMessage());
	        	 test.log(LogStatus.ERROR,e.getMessage());
		     }
		     try {
		     closeReport();
		     }
		     catch(Exception e)
		     {
		    	 e.printStackTrace();
		     }
	}
	
	public static void main(String[] args) throws Exception 
	{
		ResponsiveTestReport r = new ResponsiveTestReport();
		r.test();
	}

}
