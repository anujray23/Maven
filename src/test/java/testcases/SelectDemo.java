package testcases;
import java.sql.ResultSet;
import java.sql.Statement;

import core.Page;
public class SelectDemo extends Page
{

	public void selectdata() throws Exception
	{
makeWBConnection("C:\\Users\\ADMIN\\Desktop\\Anuj training\\Book1.xlsx");  // inherit
		
		Statement stm = con.createStatement();
		
	ResultSet rs =	stm.executeQuery("select * from Sheet1 where anuj='ram' ");
	
while(rs.next())
	{
	String x =	rs.getString("anuj");
	String y =	rs.getString("kumar");
	String a =	rs.getString(1);
	
	System.out.println(x+" - "+y+" - "+a);
	}
	
	closeWBConnection();
	}
	public static void main(String[] args) throws Exception 
	{
		SelectDemo s = new SelectDemo();
		s.selectdata();
	}

}
