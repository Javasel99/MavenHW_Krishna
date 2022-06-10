package session5_Part2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;
	String browser;
	String url;

	// Element List
	By USER_NAME_FIELD = By.xpath("//*[@id=\"username\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By ADD_CONTACT_HEADER_LOCATOR = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULL_NAME_LOCATOR = By.xpath("//*[@id=\"account\"]");
	By COMPANY_DROPDOWN_LOCATOR = By.xpath("//select[@id=\"cid\"]");
	By EMAIL_LOCATOR = By.xpath("//*[@id=\"email\"]");
	By COUNTRY_LOCATOR = By.xpath("//select[@id=\"country\"]");

	// Test Data
	String userName = "demo@techfios.com";
	String password = "abc123";
	String fullname = "dsjafl sdklfsdaj";
	String company = "Techfios";

	@BeforeSuite
	public void readConfig() {

		// FileReader //Scanner //InputStream //BufferedReader

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {
			e.getStackTrace();
		}

	}

	@BeforeTest
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(priority = 2)
	public void loginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Wrong page!!!");

	}

	@Test(priority = 1)
	public void negLoginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		// Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(),
		// "Dashboard", "Wrong page!!!");

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}