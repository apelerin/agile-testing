package test.acceptance;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

public class CaracteristicsTabSteps {

	public static WebDriver driver;

	@Before
	public void beforeScenario() {
		System.setProperty("webdriver.chrome.driver","/Library/Java/JUNIT/chromedriver");
		driver = new ChromeDriver();
		// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Given("^je suis sur la page du model (\\d+)$")
	public void je_suis_sur_la_page_du_model(int arg1) throws Throwable {
		driver.get("https://www.tesla.com/fr_FR/model" + arg1);
	}

	@When("^je sélectionne des paramètres \"([^\"]*)\",$")
	public void je_sélectionne_des_paramètres(String arg1) throws Throwable {
		driver.findElement(By.xpath("//li[@data-gtm-drawer='specs']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(., '" + arg1 + "')]")).click();
		Thread.sleep(2000);
	}

	@Then("^les caractéristiques du produit seraient \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void les_caractéristiques_du_produit_seraient(String arg1, String arg2, String arg3) throws Throwable {
		String weight = driver.findElement(By.xpath("//section[contains(@class, 'tds-tab-panel--active')]//span[contains(., 'Poids')]/following-sibling::span")).getAttribute("innerHTML");
		weight = weight.replace("&nbsp;", " ");
		assertThat(weight, containsString(arg1));
		String acceleration = driver.findElement(By.xpath("//section[contains(@class, 'tds-tab-panel--active')]//span[contains(., 'Accélération')]/..")).getAttribute("innerHTML");
		acceleration = acceleration.replace("&nbsp;", " ");
		assertThat(acceleration, containsString(arg2));
		String battery = driver.findElement(By.xpath("//section[contains(@class, 'tds-tab-panel--active')]//span[contains(., 'Autonomie')]/following-sibling::span")).getAttribute("innerHTML");
		battery = battery.replace("&nbsp;", " ");
		assertThat(battery, containsString(arg3));
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
