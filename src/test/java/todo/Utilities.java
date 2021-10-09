package todo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Utilities{


	public void takeScreenshot(String destFilePath, WebDriver driver) throws Exception {
		
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File destFile =new File(destFilePath);
		FileUtils.copyFile(srcFile, destFile);
		
	}
	
	public int count(String toCount,WebDriver driver) {
		
		driver.findElement(By.xpath("//a[text()='"+toCount+"']")).click();
		List <WebElement> list=driver.findElements(By.xpath("//ul[@class='todo-list']/li"));
		int count=list.size();
		
		return count;
		
	}


}
