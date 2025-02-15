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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EvenementSteps {

	public static WebDriver driver;

	@Before
	public void beforeScenario() {
		System.setProperty("webdriver.chrome.driver","/Library/Java/JUNIT/chromedriver");
		driver = new ChromeDriver();
		// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Given("^je suis sur la page home$")
	public void je_suis_sur_la_page_home() throws Throwable {
		driver.get("https://www.tesla.com/fr_FR/");
	}

	@When("^je clique sur évènements dans le menu burger$")
	public void je_clique_sur_évènements_dans_le_menu_burger() throws Throwable {
		driver.findElement(By.className("tds-menu-header-main--trigger_icon")).click();
		driver.findElement(By.xpath("//a[@href='/fr_fr/events']")).click();
	}

	@Then("^je devrai être sur la page https://www\\.tesla\\.com/fr_FR/events$")
	public void je_devrai_être_sur_la_page_https_www_tesla_com_fr_FR_events() throws Throwable {
		assertEquals(driver.getCurrentUrl(),"https://www.tesla.com/fr_FR/events");
	}

	@When("^une fois que j'ai choisis un lieu$")
	public void une_fois_que_j_ai_choisis_un_lieu() throws Throwable {
		WebElement inputBox = driver.findElement(By.id("edit-geoautocomplete"));
		inputBox.clear();
		inputBox.sendKeys("Paris, France");
		Thread.sleep(4000);
		inputBox.sendKeys(Keys.DOWN);
		inputBox.sendKeys(Keys.RETURN);
		Thread.sleep(4000);
	}

	@Then("^la page doit contenir les (\\d+) prochains évènements autour$")
	public void la_page_doit_contenir_les_prochains_évènements_autour(int arg1) throws Throwable {
		List<WebElement> shopList = driver.findElements(By.xpath("//div[contains(@class, 'views-row')]"));
		for (WebElement shop : shopList) {
			System.out.println("test : " + shop.getText());
		}
		assertEquals(arg1, shopList.size());
	}

	@Then("^un lien existe pour voir tous les évènements de la marque$")
	public void un_lien_existe_pour_voir_tous_les_évènements_de_la_marque() throws Throwable {
		WebElement buttonAllEvents = driver.findElement(By.className("view-all"));
		assertThat(buttonAllEvents.getText(), containsString("tous les événements"));
	}

	@When("^je clique sur Afficher plus$")
	public void je_clique_sur_Afficher_plus() throws Throwable {
		driver.findElement(By.xpath("//li[@class='pager-next first last']/a")).click();
		Thread.sleep(4000);
	}

	@Then("^plus de (\\d+) résultats d'évènement s'affichent$")
	public void plus_de_résultats_d_évènement_s_affichent(int arg1) throws Throwable {
		List<WebElement> shopList = driver.findElements(By.xpath("//div[contains(@class, 'views-row')]"));
		System.out.println("test : " + shopList.size());
		assertThat(shopList.size(), greaterThan(arg1));
		assertTrue(shopList.size() > arg1);
	}

	@Given("^je suis sur la page évènements$")
	public void je_suis_sur_la_page_évènements() throws Throwable {
		driver.get("https://www.tesla.com/fr_FR/events");
	}

	@When("^je cherche un évènement à \"([^\"]*)\"$")
	public void je_cherche_un_évènement_à(String arg1) throws Throwable {
		WebElement inputBox = driver.findElement(By.id("edit-geoautocomplete"));
		inputBox.clear();
		inputBox.sendKeys(arg1);
		Thread.sleep(4000);
		inputBox.sendKeys(Keys.DOWN);
		inputBox.sendKeys(Keys.RETURN);
		Thread.sleep(4000);
	}

	@Then("^le premier résultat de recherche indique un évènement localisé à \"([^\"]*)\"$")
	public void le_premier_résultat_de_recherche_indique_un_évènement_localisé_à(String arg1) throws Throwable {
		assertEquals(driver.findElement(By.xpath("//div[@class='location-teaser']")).getText(), arg1);
	}

	@Then("^un formulaire à droite de l'écran contient les champs suivants: \\[\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\]$")
	public void un_formulaire_à_droite_de_l_écran_contient_les_champs_suivants(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) throws Throwable {
		List<String> champs = Arrays.asList(
				arg1,
				arg2,
				arg3,
				arg4,
				arg5,
				arg6
		);
		for(String value : champs) {
			assertThat(driver.findElement(By.xpath("//label[contains(., '" + value + "')]")).getText(), containsString(value));
		}
		assertThat(driver.findElement(By.xpath("//span[contains(., '" + arg7 + "')]")).getText(), containsString(arg7));
	}

	@Then("^le formulaire contient un bouton \"([^\"]*)\" pour envoyer ces informations$")
	public void le_formulaire_contient_un_bouton_pour_envoyer_ces_informations(String arg1) throws Throwable {
		assertEquals(driver.findElement(By.id("edit-submit-td-ajax")).getAttribute("value"), arg1);
	}

	@When("^je rempli tous les champs du formulaire Soyez informé sauf l'email et que j'appuis sur Suivant$")
	public void je_rempli_tous_les_champs_du_formulaire_Soyez_informé_sauf_l_email_et_que_j_appuis_sur_Suivant() throws Throwable {
		List<WebElement> inputs = driver.findElements(By.xpath("//div[@id='test-drive-details']/div/div/input"));
		// prénom :edit-firstname-td
		// nom : edit-lastname-td
		// email : edit-usermail-td
		// tel : edit-phonenumber-td
		// SELECT pays : edit-countries-td
		// code postal : edit-zipcode-td
		// suivant : edit-submit-td-ajax

		// Je remplis mes input avec l'email vide
		for (WebElement input : inputs){
			switch(input.getAttribute("id")){
				case "edit-firstname-td" :
					input.sendKeys("prénom");
					break;
				case "edit-lastname-td" :
					input.sendKeys("nom");
					break;
				case "edit-usermail-td" :
					input.sendKeys("");
					break;
				case "edit-phonenumber-td" :
					input.sendKeys("123456789");
					break;
				case "edit-zipcode-td" :
					input.sendKeys("29000");
					break;
				default :
					System.out.println("je n'écris rien dans cet input");
			}
		}

		// je valide le formulaire
		driver.findElement(By.id("edit-submit-td-ajax")).click();
	}

	@Then("^un message textuel apparait sous le champ email indiquant \"([^\"]*)\"$")
	public void un_message_textuel_apparait_sous_le_champ_email_indiquant(String arg1) throws Throwable {
		String errorMessage = driver.findElement(By.xpath("//input[@id='edit-usermail-td']/following-sibling::ul/li")).getText();
		assertEquals(errorMessage, "obligatoire");

		// pour couleur rouge, le li a pour css : color: var(--tds-color--red10);
	}

	@When("^je cherche les events au \"([^\"]*)\"$")
	public void je_cherche_les_events_au(String arg1) throws Throwable {
		WebElement inputBox = driver.findElement(By.id("edit-geoautocomplete"));
		inputBox.clear();
		inputBox.sendKeys(arg1);
		Thread.sleep(4000);
		inputBox.sendKeys(Keys.DOWN);
		inputBox.sendKeys(Keys.RETURN);
		Thread.sleep(4000);
	}

	@Then("^j'ai des résultats$")
	public void j_ai_des_résultats() throws Throwable {
		assertTrue((driver.findElements(By.xpath("//div[@class='view-content']/div"))).size() > 0);
	}

	@When("^je clique sur le deuxième résultat, correspondant à une visio$")
	public void je_clique_sur_le_deuxième_résultat_correspondant_à_une_visio() throws Throwable {
		driver.findElement(By.xpath("//h2/a[@href='/fr_FR/event/kawasaki']")).click();
		Thread.sleep(3000);
	}

	@Then("^j'arrive sur une page d'authentification contenant \"([^\"]*)\"$")
	public void j_arrive_sur_une_page_d_authentification_contenant(String arg1) throws Throwable {
		System.out.println("test : " + driver.getCurrentUrl());
		assertThat(driver.getCurrentUrl(), containsString(arg1));
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
