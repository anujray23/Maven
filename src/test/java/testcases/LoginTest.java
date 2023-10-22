package testcases;

import java.sql.ResultSet;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import core.Page;

public class LoginTest extends Page
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
		
		makeLog("loginlog");	   
			   
		openBrowser("firefox","http://nethorizonconsultancy.com/Seeker.aspx");
	
		log.debug("browser open with url");		
	  // WebElement uid = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder1_Login1_UserName\"]"));
	
		makeWBConnection(System.getProperty("user.dir")+"//src//test//java//excel//book2.xlsx");
		
		log.debug("connected to excel");
		
		Statement stm = con.createStatement();
	
		ResultSet rs = stm.executeQuery("select * from Sheet1");
		
		while(rs.next())
		{
			
			// get data from excel
			String userid = rs.getString(1);
			String password = rs.getString(2);
			
			log.debug("Testing "+userid+" and "+password);
			
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
			    	stm.executeUpdate("insert into Sheet2(username,password,status) values('"+userid+"','"+password+"','Member')");
					closeWBConnection();  // compulsory
					  // reopen connection
				//	makeWBConnection("C:\\core\\selenium\\Book3.xls");
					makeWBConnection(System.getProperty("user.dir")+"//src//test//java//excel//book2.xlsx");
						
					stm = con.createStatement();
			log.debug("member..");
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
		LoginTest l = new LoginTest();
		l.test();
	}

}
