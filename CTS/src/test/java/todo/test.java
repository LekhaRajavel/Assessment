package todo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "Resources\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("https://todomvc.com/examples/react/#/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		WebElement ele =driver.findElement(By.xpath("//input[@class='new-todo']"));
		
		Actions act=new Actions(driver);
		act.moveToElement(ele).sendKeys("test1").sendKeys(Keys.ENTER).build().perform();
		act.moveToElement(ele).sendKeys("test2").sendKeys(Keys.ENTER).build().perform();

		ele =driver.findElement(By.xpath("//*[@value='test1']/parent::*"));
		
		act.moveToElement(ele).doubleClick().keyDown(Keys.CONTROL).sendKeys("A").keyUp(Keys.CONTROL).sendKeys("testing").build().perform();
		
		System.out.println(driver.findElement(By.xpath("//*[@value='test2']/parent::*")).getText());
		driver.findElement(By.xpath("//*[@value='test2']/parent::*//input[@type='checkbox']")).click();
		
		driver.findElement(By.xpath("//a[text()='Completed']")).click();
		System.out.println(driver.findElement(By.xpath("//*[@value='test2']/parent::*")).getText());
		
		driver.findElement(By.xpath("//a[text()='All']")).click();
		List <WebElement> toDoList=driver.findElements(By.xpath("//ul[@class='todo-list']/li"));
		
		Assert.assertEquals(toDoList.size(), 3,"wrong number of to do list in all");

	}

}
