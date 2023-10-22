package testcases;

import java.sql.ResultSet;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;

public class LoginTest1 extends Page
{

	@FindBy(xpath = xpath.AllXpath.uid)
	WebElement uid;
	
	@FindBy(xpath = xpath.AllXpath.pwd)
	WebElement pwd;
	
	@FindBy(xpath = xpath.AllXpath.btn)
	WebElement btn;
	
	@FindBy(xpath = xpath.AllXpath.err)
	WebElement err;
	
	public void test() //throws Exception
	{
		   try {
		
			   makeReport("LoginTestReport"); 
				
				test.log(LogStatus.INFO, "LoginTest called..");
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
		makeLog("loginlog");	   
			   
		openBrowser("firefox","http://nethorizonconsultancy.com/Seeker.aspx");
	
		log.debug("browser open with url");		
	  // WebElement uid = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder1_Login1_UserName\"]"));
	
		makeWBConnection(System.getProperty("user.dir")+"//src//test//java//excel//book2.xlsx");
		
		log.debug("connected to excel");
		
		Statement stm = con.createStatement();
	
		ResultSet rs = stm.executeQuery("select * from Sheet1");
		
		
		
		
		test.log(LogStatus.PASS, "Connected to WB , Getting Data to test");
		
		while(rs.next())
		{
			
			// get data from excel
			String userid = rs.getString(1);
			String password = rs.getString(2);
			
			log.debug("Testing "+userid+" and "+password);
			
			
			
			
			
			
			
			//test.log(LogStatus.INFO, "userid "+userid+" password "+password);
			
			uid.clear();
			uid.sendKeys(userid);
			pwd.clear();
			pwd.sendKeys(password);
			
			// screen shot
			takeScreenShot("beforesubmit-"+userid);
			
			btn.click();
			
			takeScreenShot("aftersubmit-"+userid);
			
			    try {
			String msg = err.getText();
			System.out.println("not member");
			log.debug(msg);
			
			
			
			
			
			//test.log(LogStatus.INFO, "not member"+ not member);
			stm.executeUpdate("insert into Sheet2(username,password,status) values('"+userid+"','"+password+"',' not Member')");
			closeWBConnection();  // compulsory
			  // reopen connection
		//	makeWBConnection("C:\\core\\selenium\\Book3.xls");
			makeWBConnection(System.getProperty("user.dir")+"//src//test//java//excel//book2.xlsx");
			
			stm = con.createStatement();
		
			    }
			    catch(Exception ee)
			    {
			    	System.out.println("member..");
			    	stm.executeUpdate("insert into Sheet2(username,password) values('"+userid+"','"+password+"'member')");
					closeWBConnection();  // compulsory
					  // reopen connection
				//	makeWBConnection("C:\\core\\selenium\\Book3.xls");
					makeWBConnection(System.getProperty("user.dir")+"//src//test//java//excel//book2.xlsx");
						
					stm = con.createStatement();
			log.debug("member..");
			
			
			
		//test.log(LogStatus.INFO, "member "+member); 
			
			
			closeReport();  
			    }
			
		}    // end of while
		   }    // end of try
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	}   // end of method..
	public static void main(String[] args) throws Exception 
	{
		LoginTest1 l = new LoginTest1();
		l.test();
	}

}
