package com.excilys.cdb.SeleniumTest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestFilterByName {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
		WebDriver driver = new ChromeDriver();	
	    driver.get("http://localhost:8080/computer-database/dashboard#");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  @Ignore
  public void testFilterByName() throws Exception {
    driver.findElement(By.id("searchbox")).click();
    driver.findElement(By.id("searchbox")).clear();
    driver.findElement(By.id("searchbox")).sendKeys("Apple");
    driver.findElement(By.id("searchsubmit")).click();
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

