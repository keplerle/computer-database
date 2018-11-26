package com.excilys.cdb.SeleniumTest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestAddWithoutName {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
		WebDriver driver = new ChromeDriver();	
	    driver.get("http://localhost:8080/computer-database/dashboard");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  @Ignore
  public void testAddWithoutName() throws Exception {

    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("introduced")).click();
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("2018-10-01");
    driver.findElement(By.id("discontinued")).click();
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("2018-10-24");
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Micro Instrumentation and Telemetry Systems");
    driver.findElement(By.id("companyId")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Company'])[1]/following::input[1]")).click();
    driver.findElement(By.linkText("Cancel")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
