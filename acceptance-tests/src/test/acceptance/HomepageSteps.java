package test.acceptance;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.lang.*;

import org.junit.Test;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

public class HomepageSteps {

	public static WebDriver driver;

	@Before
	public void beforeScenario() {
		System.setProperty("webdriver.chrome.driver","/Library/Java/JUNIT/chromedriver");
		driver = new ChromeDriver();
		// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Given("^je suis sur la homepage$")
	public void je_suis_sur_la_homepage() throws Throwable {
		driver.get("https://www.tesla.com/fr_FR/");
	}

	@Then("^le titre doit être \"([^\"]*)\"$")
	public void le_titre_doit_être(String arg1) throws Throwable {
		assertEquals(driver.getTitle(), arg1);
	}

	@Then("^la description contient \"([^\"]*)\"$")
	public void la_description_doit_être(String arg1) throws Throwable {
		// By CSS Selector
		assertEquals(driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content"),arg1);
		// By XPATH, si vous préférez...
		// assertEquals(driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content"), arg1);
	}

	@Then("^il y a plusieurs punchlines '\\[\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\]'$")
	public void il_y_a_plusieurs_punchlines(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
		List<String> listTeslaPunches = Arrays.asList(arg1, arg2, arg3, arg4, arg5);
		for(String data : listTeslaPunches) {
			assertEquals(driver.findElement(By.xpath("//h1[contains(., \"" + data + "\")]")).getAttribute("innerHTML"), data);
		}
	}

	@Then("^le menu de la navbar contient ces liens '\\[\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\]'$")
	public void le_menu_de_la_navbar_contient_liens(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
		List<String> titles = Arrays.asList(
				arg1,
				arg2,
				arg3,
				arg4,
				arg5,
				arg6
		);
		List<WebElement> allLinks = driver.findElements(By.xpath("(//ol[@class='tds-menu-header-nav--list'])[1]/li/a"));
		int nbMatches = 0;
		for (WebElement allLink : allLinks) {
			if (titles.contains(allLink.getAttribute("href"))) {
				nbMatches++;
			}
			System.out.println("test : " + allLink.getAttribute("href"));
		}
		assertEquals(nbMatches, allLinks.size());
	}

	@Then("^un burger menu permet d'accéder à différents liens '\\[\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\]'$")
	public void un_burger_menu_permet_d_accéder_à_différents_liens(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11) throws Throwable {
		List<String> listHamburgerMenu = Arrays.asList(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
		driver.findElement(By.className("tds-menu-header-main--trigger_icon")).click();
		Thread.sleep(2000);
		List<WebElement> navList = driver.findElements(By.xpath("//ol[@class='tds-menu-header-nav--list tds-menu-header-nav--parent_nav tds--hide_on_mobile']/li/a"));
		int nbResult = 0;
		for (WebElement webElement : navList) {
			if (listHamburgerMenu.contains(webElement.getText())) {
				nbResult++;
			}
			System.out.println("test : " + webElement.getText());
		}
		assertEquals(nbResult, listHamburgerMenu.size());
	}


	@After
	public void afterScenario() {
		driver.quit();
	}

}
