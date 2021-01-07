package test.acceptance;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

	@Then("^le prix affiché par défaut est un prix LOA à \"([^\"]*)\" et \"([^\"]*)\" d'économie de carburant$")
	public void le_prix_affiché_par_défaut_est_un_prix_LOA_à_et_d_économie_de_carburant(String arg1, String arg2) throws Throwable {
		assertThat(driver.findElement(By.xpath("//p[@class='finance-item--price finance-item--price-before-savings']")).getText(), containsString(arg1));
		assertThat(driver.findElement(By.xpath("//p[@class='finance-item--price']")).getText(), containsString(arg2));
	}

	@Then("^le montant total au terme du contrat est de \"([^\"]*)\"$")
	public void le_montant_total_au_terme_du_contrat_est_de(String arg1) throws Throwable {
		driver.findElement(By.xpath("//a[@class='finance-content--modal']")).click();
		Thread.sleep(2000);
		assertEquals(driver.findElement(By.id("totalLeaseAmount")).getAttribute("value"), arg1);
		driver.findElement(By.xpath("//span[@class='modal-content--close']")).click();
		Thread.sleep(2000);
	}

	@When("^je sélectionne le modèle Performance$")
	public void je_sélectionne_le_modèle_Performance() throws Throwable {
		driver.findElement(By.xpath("//span[contains(.,'Performance')]")).click();
		Thread.sleep(2000);
	}

	@Then("^le prix en LOA est à \"([^\"]*)\" et \"([^\"]*)\" d'économie de carburant$")
	public void le_prix_en_LOA_est_à_et_d_économie_de_carburant(String arg1, String arg2) throws Throwable {
		assertThat(driver.findElement(By.xpath("//p[@class='finance-item--price finance-item--price-before-savings']")).getText(), containsString(arg1));
		assertThat(driver.findElement(By.xpath("//p[@class='finance-item--price']")).getText(), containsString(arg2));
	}

	@When("^je sélectionne l'option Capacité de conduite entièrement autonome$")
	public void je_sélectionne_l_option_Capacité_de_conduite_entièrement_autonome() throws Throwable {
		driver.findElement(By.xpath("//span[contains(text(), 'Pilotage automatique')]")).click();
		for (String windowHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windowHandle);
		}
	}

	@Then("^le prix augmente de (\\d+) €/mois$")
	public void le_prix_augmente_de_€_mois(int arg1) throws Throwable {
		String numberOnly= driver.findElement(By.xpath("//p[contains(@class, 'finance-item--price') and contains(@class, 'finance-item--price-before-savings')]")).getText().replaceAll("[^0-9]", "");
		int initValue = Integer.parseInt(numberOnly);
		driver.findElement(By.xpath("//i[contains(@class, 'icon-checkbox') and contains(@class, 'option-checkbox--icon') and contains(@class, 'icon-checkbox--blue')]")).click();
		numberOnly= driver.findElement(By.xpath("//p[contains(@class, 'finance-item--price') and contains(@class, 'finance-item--price-before-savings')]")).getText().replaceAll("[^0-9]", "");
		int finalValue = Integer.parseInt(numberOnly);
		assertEquals(finalValue - initValue, arg1);
	}

	@When("^je clique sur le logo en haut à gauche$")
	public void je_clique_sur_le_logo_en_haut_à_gauche() throws Throwable {
		driver.findElement(By.xpath("//a[@href='/']")).click();

		for (String windowHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windowHandle);
		}
	}

	@Then("^j'arrive sur la page d'accueil US \"([^\"]*)\"$")
	public void j_arrive_sur_la_page_d_accueil_US(String arg1) throws Throwable {
		assertEquals(driver.getCurrentUrl(), arg1);
	}

	@When("^je clique sur le lien localisations en bas de page$")
	public void je_clique_sur_le_lien_localisations_en_bas_de_page() throws Throwable {

		// gérer cas popup de sélection de région
		if (driver.findElements(By.xpath("//i[@class='tds-modal-close-icon']")).size() != 0){
			driver.findElement(By.xpath("//i[@class='tds-modal-close-icon']")).click();
		}
		//-------//

		for(int i = 0; i < 6; i++){
			driver.findElement(By.id("page-new-homepage")).click();
			WebElement test = driver.findElement(By.id("page-new-homepage"));
			test.sendKeys(Keys.DOWN);
			Thread.sleep(2500);
		}

		driver.findElement(By.xpath("//a[@href='/findus/list']")).click();
		Thread.sleep(5000);
	}

	@Then("^l'url est \"([^\"]*)\"$")
	public void l_url_est(String arg1) throws Throwable {
		// j'ai retiré le fr_FR de l'url car lors du clic sur logo on se retrouve sur tesla.com
		assertEquals(driver.getCurrentUrl(), arg1);
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
