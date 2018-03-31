package systemTests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class TestH2CrearParticular {
	private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
	    driver = new HtmlUnitDriver();
	    baseUrl = "https://www.katalon.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testRegistrarParticularSinMeterCampos() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-home-tab")).click();
	    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
	  }
	  
	  @Test
	  public void testRegistrarParticularMalFormatoDeCampos() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-home-tab")).click();
	    driver.findElement(By.id("nombre")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("1");
	    driver.findElement(By.id("apellidos")).click();
	    driver.findElement(By.id("apellidos")).clear();
	    driver.findElement(By.id("apellidos")).sendKeys("8");
	    driver.findElement(By.id("dni")).click();
	    driver.findElement(By.id("dni")).clear();
	    driver.findElement(By.id("dni")).sendKeys("1");
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).sendKeys("1");
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).sendKeys("1");
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).sendKeys("1");
	    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
	  }

	  @Test
	  public void testRegistrarParticularContrasenasNoCoincidentes() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-home-tab")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("a");
	    driver.findElement(By.id("apellidos")).click();
	    driver.findElement(By.id("apellidos")).clear();
	    driver.findElement(By.id("apellidos")).sendKeys("a");
	    driver.findElement(By.id("dni")).click();   
	    driver.findElement(By.id("dni")).clear();
	    driver.findElement(By.id("dni")).sendKeys("12345678A");
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).sendKeys("pruebaSP@gmail.com");
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).sendKeys("a");
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).sendKeys("1");
	    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
	  }
	  
	  @Test
	  public void testRegistrarParticularCorrecto() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-home-tab")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("Prueba");
	    driver.findElement(By.id("apellidos")).click();
	    driver.findElement(By.id("apellidos")).clear();
	    driver.findElement(By.id("apellidos")).sendKeys("Prueba Prueba");
	    driver.findElement(By.id("dni")).click();
	    driver.findElement(By.id("dni")).clear();
	    driver.findElement(By.id("dni")).sendKeys("12345678A");
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).sendKeys("pruebaSP@gmail.com");
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).sendKeys("12345A");
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).sendKeys("12345A");
	    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
	  }
	  
	  @Test
	  public void testRegistroParticularEmailYDNIRepetidos() throws Exception {
		driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-home-tab")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("Prueba");
	    driver.findElement(By.id("apellidos")).click();
	    driver.findElement(By.id("apellidos")).clear();
	    driver.findElement(By.id("apellidos")).sendKeys("Prueba Prueba");
	    driver.findElement(By.id("dni")).click();
	    driver.findElement(By.id("dni")).clear();
	    driver.findElement(By.id("dni")).sendKeys("12345678A");
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='email'])[2]")).sendKeys("pruebaSP@gmail.com");
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='password'])[2]")).sendKeys("12345A");
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='passwordConfirmacion'])[2]")).sendKeys("12345A");
	    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
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
