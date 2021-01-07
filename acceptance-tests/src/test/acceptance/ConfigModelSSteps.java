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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ConfigModelSSteps {

	public static WebDriver driver;

	@Before
	public void beforeScenario() {
		System.setProperty("webdriver.chrome.driver","/Library/Java/JUNIT/chromedriver");
		driver = new ChromeDriver();
		// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Given("^je suis sur la page du Model S$")
	public void je_suis_sur_la_page_du_Model_S() throws Throwable {
		driver.get("https://www.tesla.com/fr_FR/models");
	}

	@When("^je clique sur commander$")
	public void je_clique_sur_commander() throws Throwable {
		driver.findElement(By.xpath("//a[@href='/fr_fr/models/design']")).click();
		for (String windowHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windowHandle);
		}
	}

	@Then("^l'url devrait être \"([^\"]*)\"$")
	public void l_url_devrait_être(String arg1) throws Throwable {
		assertEquals(driver.getCurrentUrl(), arg1);
	}

	@Then("^le prix affiché par défaut est un prix LOA à \"([^\"]*)\"$")
	public void le_prix_affiché_par_défaut_est_un_prix_LOA_à(String arg1) throws Throwable {
		WebElement price = driver.findElement(By.xpath("//p[@class='finance-item--price finance-item--price-before-savings']"));
		assertThat(price.getText(), containsString(arg1));
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
