package testcases;


import java.sql.Statement;

import core.Page;

public class InsertDemo extends Page
{
	
	public void insertData() throws Exception
	{
		makeWBConnection("C:\\Users\\ADMIN\\Desktop\\Anuj Training\\Book1.xlsx");  // inherit
		Statement stm = con.createStatement();// statement work is to make create/insert statement
		stm.executeUpdate("insert into Sheet1(anuj,kumar,srivastava)values('aefg','moo','ghjh')");
		closeWBConnection();//compulsory
		System.out.println("data inserted...");
	}

	public static void main(String[] args) throws Exception 
	{
		InsertDemo i = new InsertDemo();
		i.insertData();  // to insert data excel must be closed
	}

}
