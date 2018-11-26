package com.excilys.cdb.SeleniumTest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestEditWrongDate {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
		WebDriver driver = new ChromeDriver();	
	    driver.get("http://localhost:8080/webapp/dashboard");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test

  public void testEditWrongDate() throws Exception {

    driver.findElement(By.linkText("CM-200")).click();
    driver.findElement(By.id("introduced")).click();
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("2018-10-31");
    driver.findElement(By.id("discontinued")).click();
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("2018-10-01");
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
