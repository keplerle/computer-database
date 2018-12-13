package com.excilys.cdb.SeleniumTest;

import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.config.RootConfig;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class TestPagination {
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
	@Ignore
	public void testPagination() throws Exception {
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("user"));
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("password"));
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Password'])[1]/following::button[1]"))
				.click();
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
		driver.findElement(By.xpath(
				"(.//*[normalize-space(text()) and normalize-space(.)='Commodore International'])[1]/following::button[2]"))
				.click();
		driver.findElement(By.xpath(
				"(.//*[normalize-space(text()) and normalize-space(.)='Commodore International'])[1]/following::button[3]"))
				.click();
		driver.findElement(By.linkText("5")).click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Nintendo'])[1]/following::span[2]"))
				.click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='justName'])[1]/following::span[2]"))
				.click();
		driver.findElement(By.id("searchbox")).click();
		driver.findElement(By.id("searchbox")).clear();
		driver.findElement(By.id("searchbox")).sendKeys("ok");
		driver.findElement(By.id("searchsubmit")).click();
		driver.findElement(By.xpath(
				"(.//*[normalize-space(text()) and normalize-space(.)='Fujitsu Lifebook T900'])[1]/following::button[1]"))
				.click();
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
