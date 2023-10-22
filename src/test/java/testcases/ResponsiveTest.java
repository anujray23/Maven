package testcases;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import core.Page;

public class ResponsiveTest  extends Page
{
    // global
	@FindBy(xpath = xpath.AllXpath.imgxpath)
	WebElement img;
	
	public void test() throws Exception
	{
		//makeWBConnection("C:\\core\\selenium\\responsive1.xlsx"); // open connection with WB
	        // or
		makeWBConnection(System.getProperty("user.dir")+"\\src\\test\\java\\excel\\book21.xlsx");
		Statement stm =	con.createStatement();
	ResultSet rs = stm.executeQuery("select * from Sheet1");
	
	while(rs.next())
	{
		// read data from excel
		String browser = rs.getString(1);
		String url = rs.getString(2);
		String resolution = rs.getString(3);
		// break resolution..
		StringTokenizer str = new StringTokenizer(resolution,",");
		int w = Integer.parseInt(str.nextToken().trim());
		int h = Integer.parseInt(str.nextToken().trim());
		
		String expwidth = rs.getString(4);  // expected data
		String expheight = rs.getString(5);  // expected data
		
		openBrowser(browser,url); // inherited from Page
		driver.manage().window().setSize(new Dimension(w,h));
		
		takeScreenShot("img-"+resolution); // inherited from Page to take screen shot
		
		  // get data from website
	//	WebElement img = driver.findElement(By.xpath("//*[@id=\"band\"]/div/div[1]/img"));
	          // or...
		String actualwidth = img.getCssValue("width");
		String actualheight = img.getCssValue("height");
		
		 if(expwidth.equals(actualwidth) && expheight.equals(actualheight))
		 {
			 // pass
	stm.executeUpdate("insert into Sheet2(resolution,expected,actual,status) values('"+resolution+"','"+expwidth+" x "+expheight+"','"+actualwidth+" x "+actualheight+"','Pass')");		 
		closeWBConnection(); // compulsory to commit and close connection
		 
		// reopen connection
		//makeWBConnection("C:\\core\\selenium\\responsive1.xlsx"); // open connection with WB
		      // or...
		makeWBConnection(System.getProperty("user.dir")+"\\src\\test\\java\\excel\\book21.xlsx");
		stm = con.createStatement();
	 
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
			
		 }
	}  // end of while
	
	}
	
	public static void main(String[] args) throws Exception 
	{
		ResponsiveTest r = new ResponsiveTest();
		r.test();
	}

}
