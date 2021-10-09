package todo;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ManageToDoLists {

	static WebDriver driver;
	static Actions act;
	static WebElement element;
	static FileInputStream fi;
	static XSSFWorkbook wb;
	static XSSFSheet sh1;
	Utilities utilityClassObject =new Utilities();
	@BeforeSuite
	public void setup() {

		System.setProperty("webdriver.chrome.driver", "Resources\\chromedriver.exe");

		driver = new ChromeDriver();

		driver.get("https://todomvc.com/examples/react/#/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@AfterSuite
	public void tearDown() {

		driver.quit();
	}
	
	@BeforeTest
	public void openTestDatafile() throws Exception {
		fi = new FileInputStream("Resources\\TestData.xlsx");
		wb = new XSSFWorkbook(fi);

		sh1 = wb.getSheetAt(0);
	}

	@AfterTest
	public void closeTestDatafile() throws Exception {

		wb.close();
		fi.close();

	}

	
// 1. to create new to do's	
	@Test (priority = -1)
	public void createToDos() throws Exception {

		element = driver.findElement(By.xpath("//input[@class='new-todo']"));
		act = new Actions(driver);

		System.out.println(sh1.getPhysicalNumberOfRows());
		for (int i = 1; i <= sh1.getLastRowNum(); i++) {
			Row row = sh1.getRow(i);
			act.moveToElement(element).sendKeys(row.getCell(0).getStringCellValue()).sendKeys(Keys.ENTER).build()
					.perform();
		}
		
		utilityClassObject.takeScreenshot("Resources\\ToDosCreated.png", driver);
	}

	
// 2. edit the created todo's
	@Test()
	public void editToDo() throws Exception {

		element = driver.findElement(By.xpath("//*[@value='Test1']/parent::*"));
		act.moveToElement(element).doubleClick().keyDown(Keys.CONTROL).sendKeys("A").keyUp(Keys.CONTROL)
				.sendKeys("testing").build().perform();
		
		utilityClassObject.takeScreenshot("Resources\\ToDosEdited.png",driver);


	}

	
// 3. Complete the todo's
	@Test(dependsOnMethods = {"editToDo"})
	public void completeToDo() throws Exception {
		
		driver.findElement(By.xpath("//*[@value='Test2']/parent::*//input[@type='checkbox']")).click();
		utilityClassObject.takeScreenshot("Resources\\ToDosCompleted.png",driver);

	}
	
//4. Compare todo's to verify---> completed + active = all todo
	
	@Test
	public void verificationOfTodoCount() {
		
		
		Assert.assertEquals(utilityClassObject.count("All",driver), (utilityClassObject.count("Active",driver)+utilityClassObject.count("Completed",driver)));
	}
	
//5.clear completed ToDo's
	
	@Test(dependsOnMethods = {"completeToDo","verificationOfTodoCount"})
	public void clearCompleted() throws Exception {
		

		driver.findElement(By.xpath("//button[@class='clear-completed']")).click();
		utilityClassObject.takeScreenshot("Resources\\CompletedTodos.png",driver);
		utilityClassObject.takeScreenshot("Resources\\CompletedTodosCleared.png",driver);

		
	}
	
}
