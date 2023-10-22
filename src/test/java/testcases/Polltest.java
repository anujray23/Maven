package testcases;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import core.Page;

public class Polltest extends Page
{
	public void polling()
	{
	openBrowser("chrome","https://timesofindia.indiatimes.com/poll.cms");
	String txt = driver.findElement(By.xpath("//*[@id=\"pollform\"]/table/tbody/tr[1]/td")).getText(); // get method will help itdg kgjjgkg gjgjj 
	System.out.println(txt);
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter Agree or Disagreee");
	String x= sc.next().trim();// input from user
	if (x.equals("Agree"))
	{
		driver.findElement(By.xpath("//*[@type='radio'and @value='2']")).click();
		
	}
	else
	{
		driver.findElement(By.xpath("//*[@type='radio'and @value='1']")).click();
	}
	String q = driver.findElement(By.xpath("//*[@id=\"mathq2\"]")).getText();
	System.out.println(q);
	StringTokenizer str = new StringTokenizer(q,"+=");
	int no1 =Integer.parseInt(str.nextToken().trim());
	int no2 = Integer.parseInt(str.nextToken().trim());
	int sum = no1+no2;
	// convert int to string
	String sum1 = Integer.toString(sum);
	System.out.println(sum);
	driver.findElement(By.xpath("//*[@id=\"mathuserans2\"]")).sendKeys(sum1);// send can print string type of value
	driver.findElement(By.xpath("//*[@pg='Vote']")).click();
	WebElement y = driver.findElement(By.xpath("//*[text()='You have successfully cast your vote.']"));
	if (y.isDisplayed())
	{
		System.out.println(y.getText());
		System.out.println("Pass");
	}
	else
	{
	System.out.println("Fail");	
	}
	}
	
	public static void main(String[] args) 
	{
		Polltest p = new Polltest();
		p.polling();
	}

}
