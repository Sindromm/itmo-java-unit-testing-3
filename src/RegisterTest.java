import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.io.File;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.support.ui.Select;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    String os = System.getProperty("os.name");
    if (os.equals("Linux")) {
        File pathToBinary = new File("/usr/bin/firefox");
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        driver = new FirefoxDriver(ffBinary,firefoxProfile);
    }
    else {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("load-extension=C:/Users/Fedor/Documents/itmo-java-unit-testing-3/lib/uBlock0.chromium");
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
      driver = new ChromeDriver(capabilities);
    }

    baseUrl = "https://www.tutu.ru/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testRegister() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//div[@class='l-page_wrapper']/div[1]/div/div[2]/div[1]/div[3]/div/div/div/div[1]/div/div/a")).click();
    for (int second = 0;; second++) {
      if (second >= 60) fail("timeout");
      try { if (isElementPresent(By.xpath("//div[@class='reg_wrp j-auth-register s-active']/div/div[1]/div[1]/form/input[2]"))) break; } catch (Exception e) {}
      Thread.sleep(1000);
    }
    
    String email = "javaTestLab+" +  Math.floor(Math.random()*11111) + "@yopmail.com";
    driver.findElement(By.xpath("//div[@class='reg_wrp j-auth-register s-active']/div/div[1]/div[1]/form/input[2]")).sendKeys(email);
    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='reg_wrp j-auth-register s-active']/div/div[1]/div[1]/form/div[3]/button")).click();
    Thread.sleep(2000);
    driver.navigate().refresh();
    try {
      assertTrue(driver.findElement(By.xpath("//div[@class='l-page_wrapper']/div[1]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div/div/a")).getText().matches("^javaTestLab[\\s\\S]*yopmail\\.com$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @Test
  public void testRegisterDelete() throws Exception {
    testRegister(); //in case of no cookies
    driver.get(baseUrl + "/");
    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='l-page_wrapper']//div[1]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div/div/a")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//div[@class='j-menu-profile']/li/a"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    driver.findElement(By.xpath("//div[@class='j-menu-profile']/li/a")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//div[@class='b-personal']/div/div/a"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='b-personal']/div/div/a")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//div[@class='b-user-login_new m-mrn b-user-login']/a[2]"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='b-user-login_new m-mrn b-user-login']/a[2]")).click();
    Thread.sleep(2000);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
