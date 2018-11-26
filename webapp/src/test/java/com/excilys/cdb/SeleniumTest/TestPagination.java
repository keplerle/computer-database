package com.excilys.cdb.SeleniumTest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestPagination {
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
  public void testPagination() throws Exception {
    driver.findElement(By.linkText("1")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.linkText("3")).click();
    driver.findElement(By.linkText("4")).click();
    driver.findElement(By.linkText("5")).click();
    driver.findElement(By.linkText("3")).click();
    driver.findElement(By.linkText("«")).click();
    driver.findElement(By.linkText("«")).click();
    driver.findElement(By.linkText("«")).click();
    driver.findElement(By.linkText("«")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Commodore International'])[1]/following::button[2]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Commodore International'])[1]/following::button[3]")).click();
    driver.findElement(By.linkText("5")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Nintendo'])[1]/following::span[2]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='justName'])[1]/following::span[2]")).click();
    driver.findElement(By.id("searchbox")).click();
    driver.findElement(By.id("searchbox")).clear();
    driver.findElement(By.id("searchbox")).sendKeys("ok");
    driver.findElement(By.id("searchsubmit")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Fujitsu Lifebook T900'])[1]/following::button[1]")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("»")).click();
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
