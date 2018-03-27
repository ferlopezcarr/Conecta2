package systemTest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PruebasRegistroEmpresa {
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
	  public void testRegistrarEmpresaSinMeterCampos() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-profile-tab")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	  
	  @Test
	  public void testRegistrarEmpresaMalFormatoDeCampos() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-profile-tab")).click();
	    driver.findElement(By.id("nombre")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("a");
	    driver.findElement(By.id("cif")).click();
	    driver.findElement(By.id("cif")).clear();
	    driver.findElement(By.id("cif")).sendKeys("a");
	    driver.findElement(By.id("email")).click();
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys("a");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("a");
	    driver.findElement(By.id("passwordConfirmacion")).click();
	    driver.findElement(By.id("passwordConfirmacion")).clear();
	    driver.findElement(By.id("passwordConfirmacion")).sendKeys("a");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	  
	  @Test
	  public void testRegistrarEmpresaContrasenasNoCoincidentes() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-profile-tab")).click();
	    driver.findElement(By.id("nombre")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("a");
	    driver.findElement(By.id("cif")).click();
	    driver.findElement(By.id("cif")).clear();
	    driver.findElement(By.id("cif")).sendKeys("A12345678");
	    driver.findElement(By.id("email")).click();
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys("pruebaSEmpresa@gmail.com");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("a");
	    driver.findElement(By.id("passwordConfirmacion")).click();
	    driver.findElement(By.id("passwordConfirmacion")).clear();
	    driver.findElement(By.id("passwordConfirmacion")).sendKeys("1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	  
	  @Test
	  public void testRegistrarEmpresaContraseAsNoCoincidentes() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-profile-tab")).click();
	    driver.findElement(By.id("nombre")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("a");
	    driver.findElement(By.id("cif")).click();
	    driver.findElement(By.id("cif")).clear();
	    driver.findElement(By.id("cif")).sendKeys("A12345678");
	    driver.findElement(By.id("email")).click();
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys("pruebaSEmpresa@gmail.com");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("a");
	    driver.findElement(By.id("passwordConfirmacion")).click();
	    driver.findElement(By.id("passwordConfirmacion")).clear();
	    driver.findElement(By.id("passwordConfirmacion")).sendKeys("1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	  
	  @Test
	  public void testRegistroEmpresaEmailYCIFRepetidos() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.linkText("Crear cuenta")).click();
	    driver.findElement(By.id("pills-profile-tab")).click();
	    driver.findElement(By.id("nombre")).click();
	    driver.findElement(By.id("nombre")).clear();
	    driver.findElement(By.id("nombre")).sendKeys("Prueba");
	    driver.findElement(By.id("cif")).click();
	    driver.findElement(By.id("cif")).clear();
	    driver.findElement(By.id("cif")).sendKeys("P12345678");
	    driver.findElement(By.id("email")).click();
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys("pruebaSEmpresa@gmail.com");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("A12345");
	    driver.findElement(By.id("passwordConfirmacion")).click();
	    driver.findElement(By.id("passwordConfirmacion")).clear();
	    driver.findElement(By.id("passwordConfirmacion")).sendKeys("A12345");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
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
