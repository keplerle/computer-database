package com.excilys.cdb.SeleniumTest;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.config.RootConfig;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class TestAddWrongDate {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();
	@Autowired
	private Properties properties;
  @Before
  public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
		driver = new ChromeDriver();	
	    driver.get("http://localhost:8080/computer-database/dashboard");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddWrongDate() throws Exception {
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("user"));
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("password"));
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Password'])[1]/following::button[1]"))
				.click();
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("computerName")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("TestSelenium");
    driver.findElement(By.id("introduced")).click();
    driver.findElement(By.id("introduced")).click();
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("2018-10-31");
    driver.findElement(By.id("discontinued")).click();
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("2018-10-01");
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Lincoln Laboratory");
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
