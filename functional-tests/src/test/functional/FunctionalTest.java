package test.functional;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionalTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver","/Library/Java/JUNIT/chromedriver");
		driver = new ChromeDriver();
	    	// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
  		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
     }

    // Test de la Story #1-homepage (https://trello.com/c/WKTneu9o/1-homepage)
	@Test
    public void testHomepage() throws Exception {
        driver.get("https://www.tesla.com/fr_FR/");

        ///////////////////////////////////////////////////////////////
        assertEquals(driver.getTitle(), "Voitures électriques, énergie solaire et propre | Tesla France");

        ///////////////////////////////////////////////////////////////
        assertEquals(driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content"), "Tesla accélère la transition mondiale vers une énergie durable en proposant des véhicules électriques, des panneaux solaires et des solutions intégrées d'énergie renouvelable pour les particuliers et les entreprises.");
        
        ///////////////////////////////////////////////////////////////
        List<String> listTeslaPunches = Arrays.asList("Model 3", "Model S", "Model X", "Model Y", "Systèmes d'énergie solaire et Powerwalls");
        for(String data : listTeslaPunches) {
            assertEquals(driver.findElement(By.xpath("//h1[contains(., \"" + data + "\")]")).getAttribute("innerHTML"), data);
        }

        ///////////////////////////////////////////////////////////////
        List<String> titles = Arrays.asList(
            "https://www.tesla.com/fr_fr/models", 
            "https://www.tesla.com/fr_fr/model3",
            "https://www.tesla.com/fr_fr/modelx",
            "https://www.tesla.com/fr_fr/modely",
            "https://www.tesla.com/fr_fr/powerwall",
            "https://www.tesla.com/fr_fr/charging"
            );
        List<WebElement> allLinks = driver.findElements(By.xpath("(//ol[@class='tds-menu-header-nav--list'])[1]/li/a"));
        int nbMatches = 0;
        for (int i = 0; i < allLinks.size(); i++){
            if(titles.contains(allLinks.get(i).getAttribute("href"))){
                nbMatches++;
            }
            System.out.println("test : " + allLinks.get(i).getAttribute("href"));
        }
        assertEquals(nbMatches, allLinks.size());

        ///////////////////////////////////////////////////////////////
        List<String> listHamburgerMenu = Arrays.asList("VÉHICULES DISPONIBLES", "VÉHICULES D'OCCASION", "REPRISE", "CYBERTRUCK", "ROADSTER", "ÉNERGIE", "ESSAIS", "FLOTTES & ENTREPRISES", "NOUS TROUVER", "ÉVÉNEMENTS", "ASSISTANCE");
        driver.findElement(By.className("tds-menu-header-main--trigger_icon")).click();
        Thread.sleep(2000);
        List<WebElement> navList = driver.findElements(By.xpath("//ol[@class='tds-menu-header-nav--list tds-menu-header-nav--parent_nav tds--hide_on_mobile']/li/a"));
        int nbResult = 0;
        for (int i = 0; i < navList.size(); i++){
            if(listHamburgerMenu.contains(navList.get(i).getText())){
                nbResult++;
            }
            System.out.println("test : " + navList.get(i).getText());
        }
        assertEquals(nbResult, listHamburgerMenu.size());
    }

    // Test de la Story #2-homepage (https://trello.com/c/g4fKVeQP/8-configurateur-tesla-model-s)
    @Test
    public void testModel() throws Exception {
        driver.get("https://www.tesla.com/fr_fr/models/design#autopilot");

        String numberOnly= driver.findElement(By.xpath("//p[contains(@class, 'finance-item--price') and contains(@class, 'finance-item--price-before-savings')]")).getText().replaceAll("[^0-9]", "");
        int initValue = Integer.parseInt(numberOnly);
        driver.findElement(By.xpath("//i[contains(@class, 'icon-checkbox') and contains(@class, 'option-checkbox--icon') and contains(@class, 'icon-checkbox--blue')]")).click();
        numberOnly= driver.findElement(By.xpath("//p[contains(@class, 'finance-item--price') and contains(@class, 'finance-item--price-before-savings')]")).getText().replaceAll("[^0-9]", "");
        int finalValue = Integer.parseInt(numberOnly);
        assertEquals(finalValue - initValue, 89);
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
