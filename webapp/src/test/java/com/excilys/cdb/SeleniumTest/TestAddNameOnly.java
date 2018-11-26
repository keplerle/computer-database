package com.excilys.cdb.SeleniumTest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAddNameOnly {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
		WebDriver driver = new ChromeDriver();	
	    driver.get("http://localhost:8080/computer-database/dashboard?search=Apple");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  @Ignore
  public void testAddNameOnly() throws Exception {

    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("computerName")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("TestSelenium");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Company'])[1]/following::input[1]")).click();
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
