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

        // CA n°1

        driver.findElement(By.xpath("//a[@href='/fr_FR/models/design']")).click();

        assertEquals(driver.getCurrentUrl(),"https://www.tesla.com/fr_FR/models/design");

        // ----------------------- //

        // CA n°2 - Par défaut le prix affiché est un prix LOA à 768€/mois.
        
        driver.findElement(By.xpath("//a[@href='/fr_fr/models/design']")).click();

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        Thread.sleep(2000);

        WebElement price = driver.findElement(By.xpath("//p[@class='finance-item--price finance-item--price-before-savings']"));
        System.out.println("Result : " + price.getText());

        // Faire une regex ? 
        assertThat(price.getText(), containsString("768 € /mois"));

        // -------------------------------- //


        // CA n°3
        // Lorsque je sélectionne le modèle Grande Autonomie Plus, 
        // le prix en LOA est à 768€/mois et 108€/mois d'économies de carburant estimées 
        //et un montant total avec achat au terme du contrat de 94 841€, 
        //Lorsque je sélectionne le modèle Performance, le prix en LOA est à 969€/mois 
        //et 108€/mois d'économies de carburant estimées et un montant total avec achat au terme du contrat de 114 052€.

        assertEquals(driver.findElement(By.xpath("//p[@class='finance-item--price finance-item--price-before-savings']")).getText(), "A partir de 768 € /mois*");
        assertEquals(driver.findElement(By.xpath("//p[@class='finance-item--price']")).getText(), "A partir de - 108 € /mois");
        assertEquals(driver.findElement(By.xpath("(//span[@class='finance-content--disclaimer'])[2]")).getText(), "Économies de carburant estimées");
        driver.findElement(By.xpath("//a[@class='finance-content--modal']")).click();
        Thread.sleep(2000);
        assertEquals(driver.findElement(By.id("totalLeaseAmount")).getAttribute("value"), "94 841 €");

        driver.findElement(By.xpath("//span[@class='modal-content--close']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//span[contains(.,'Performance')]")).click();
        Thread.sleep(2000);
        assertEquals(driver.findElement(By.xpath("//p[@class='finance-item--price finance-item--price-before-savings']")).getText(), "A partir de 969 € /mois*");
        assertEquals(driver.findElement(By.xpath("//p[@class='finance-item--price']")).getText(), "A partir de - 108 € /mois");
        assertEquals(driver.findElement(By.xpath("(//span[@class='finance-content--disclaimer'])[2]")).getText(), "Économies de carburant estimées");
        driver.findElement(By.xpath("//a[@class='finance-content--modal']")).click();
        assertEquals(driver.findElement(By.id("totalLeaseAmount")).getAttribute("value"), "114 052 €");

        // -------------------------------- //

        // CA n° 4 - Lorsque je vais dans l'onglet Pilotage automatique 
        // et que je sélectionne l'option Capacité de conduite entièrement autonome, le prix augmente de 89€/mois.
        driver.get("https://www.tesla.com/fr_fr/models/design#autopilot");

        String numberOnly= driver.findElement(By.xpath("//p[contains(@class, 'finance-item--price') and contains(@class, 'finance-item--price-before-savings')]")).getText().replaceAll("[^0-9]", "");
        int initValue = Integer.parseInt(numberOnly);
        driver.findElement(By.xpath("//i[contains(@class, 'icon-checkbox') and contains(@class, 'option-checkbox--icon') and contains(@class, 'icon-checkbox--blue')]")).click();
        numberOnly= driver.findElement(By.xpath("//p[contains(@class, 'finance-item--price') and contains(@class, 'finance-item--price-before-savings')]")).getText().replaceAll("[^0-9]", "");
        int finalValue = Integer.parseInt(numberOnly);
        assertEquals(finalValue - initValue, 89);
        // --------------------------- //

        // CA n°5 - Une fois que j'ai configuré mon modèle S, je souhaiterais connaitre la liste des localisations de vente. 
        //Je clic donc sur le logo en haut à gauche de la page puis en bas de page sur le lien Localisations 
        //et j'arrive sur la page dont l'url est : https://www.tesla.com/fr_FR/findus/list

        driver.findElement(By.xpath("//a[@href='/fr_fr/models/design']")).click();

        for (String windowHandle : driver.getWindowHandles()) {
        driver.switchTo().window(windowHandle);
        }

        driver.findElement(By.xpath("//a[@href='/']")).click();

        for (String windowHandle : driver.getWindowHandles()) {
        driver.switchTo().window(windowHandle);
        }

        // gérer cas popup de sélection de région
        if (driver.findElements(By.xpath("//i[@class='tds-modal-close-icon']")).size() != 0){
        driver.findElement(By.xpath("//i[@class='tds-modal-close-icon']")).click();
        }
        //-------//

        // JavascriptExecutor js=(JavascriptExecutor)driver;
        // js.executeScript("window.scrollBy(0,400)");

        for(int i = 0; i < 6; i++){
        driver.findElement(By.id("page-new-homepage")).click();
        WebElement test = driver.findElement(By.id("page-new-homepage"));
        test.sendKeys(Keys.DOWN);
        Thread.sleep(2500);
        }

        driver.findElement(By.xpath("//a[@href='/findus/list']")).click();
        Thread.sleep(5000);
        // j'ai retiré le fr_FR de l'url car lors du clic sur logo on se retrouve sur tesla.com
        assertEquals(driver.getCurrentUrl(), "https://www.tesla.com/findus/list");

        // --------------------- //

    }

    // Test Story #3
    @Test
    public void testEventpage() throws Exception {
        driver.get("https://www.tesla.com/fr_FR/events");

        // CA n°1

        driver.findElement(By.className("tds-menu-header-main--trigger_icon")).click();
        driver.findElement(By.xpath("//a[@href='/fr_fr/events']")).click();

        assertEquals(driver.getCurrentUrl(),"https://www.tesla.com/fr_FR/events");

        // ------------------------------ //


        // CA n°2
        // La page doit contenir les 15 prochains événement autour de chez soi, 
        //une fois que l'on choisi un lieu dans le monde

        WebElement inputBox = driver.findElement(By.id("edit-geoautocomplete"));
        inputBox.clear();
        inputBox.sendKeys("Paris");
        Thread.sleep(4000);
        inputBox.sendKeys(Keys.DOWN);
        inputBox.sendKeys(Keys.RETURN);
        Thread.sleep(4000);

        List<WebElement> shopList = driver.findElements(By.xpath("//div[@class='node node-event node-teaser clearfix']/div/h2/a"));
            for (WebElement shop : shopList) {
            System.out.println("test : " + shop.getText());
        }
        assertEquals(15, shopList.size());

        // ------------------------------ //

        // CA n°3
        //Un lien permet d'afficher tous les événement de la marque 
        //et un deuxième lien permet d'afficher plus que 15 résultats de recherche.

        // On vérifie la présence d'un lien vers tous les events
        WebElement buttonAllEvents = driver.findElement(By.className("view-all"));
        assertThat(buttonAllEvents.getText(), containsString("tous les événements"));

        // On vérifie la présence d'un lien pour afficher plus de resultats
        WebElement displayMore = driver.findElement(By.xpath("//li[@class='pager-next first last']/a"));
        assertThat(displayMore.getText(), containsString("AFFICHER PLUS"));

        // On vérifie qu'en cliquant on a + de 15 résultats
        driver.findElement(By.className("view-all")).click();
        Thread.sleep(4000);
        driver.findElement(By.xpath("//li[@class='pager-next first last']/a")).click();
        Thread.sleep(4000);
        List<WebElement> shopList = driver.findElements(By.xpath("//div[@class='node node-event node-teaser clearfix']/div/h2/a"));
        System.out.println("test : " + shopList.size());
        assertTrue(shopList.size() > 15);

        // ------------------------------ //

        // CA n°4

        // à regarder depuis les tests d'acceptance
        // List<WebElement> labels = driver.findElements(By.xpath("//div[@id='test-drive-details']/div/label"));
        // labels.add(driver.findElement(By.xpath("//label[@class='tds-label tds-label--checkbox']")));
        // //labels.add((driver.findElement(By.id("edit-submit-td-ajax"))).getAttribute("value"));

        // for (WebElement label : labels){
        //     System.out.println(label.getText());
        // }

        // ------------------------------ //


        // CA n°5
        // Lorsque je rempli tous les champs du formulaire Soyez informé, sauf l'email et que j'appui sur Suivant, 
        // un message textuel en rouge apparait sous le champ email indiquant obligatoire.

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

        String errorMessage = driver.findElement(By.xpath("//input[@id='edit-usermail-td']/following-sibling::ul/li")).getText();
        assertEquals(errorMessage, "obligatoire");

        // pour couleur rouge, le li a pour css : color: var(--tds-color--red10);

        // ------------------------------ //

        // CA n°6
        // Lorsque je cherche un événement à Londres, Royaume-Uni, 
        // le premier résultat de recherche indique un événement localisé à Royaume-Uni

        WebElement inputBox = driver.findElement(By.id("edit-geoautocomplete"));
        inputBox.clear();
        inputBox.sendKeys("Londres");
        Thread.sleep(4000);
        inputBox.sendKeys(Keys.DOWN);
        inputBox.sendKeys(Keys.RETURN);
        Thread.sleep(4000);

        assertEquals(driver.findElement(By.xpath("//div[@class='location-teaser']")).getText(), "Royaume-Uni");

        // ------------------------------ //

        // CA 7 - Je souhaite m'inscrire à un événement qui aura lieu au Japon, je fais donc la recherche pour le Japon, puis je clic sur le lien inscription de cet événement. 
        // Je suis alors redirigé vers la page de connexion à mon compte Tesla (https://auth.tesla.com/....)

        WebElement inputBox = driver.findElement(By.id("edit-geoautocomplete"));
        inputBox.clear();
        inputBox.sendKeys("Japon");
        Thread.sleep(4000);
        inputBox.sendKeys(Keys.DOWN);
        inputBox.sendKeys(Keys.RETURN);
        Thread.sleep(4000);

        // je sais que c'est cette div... pas la plus belle des manières...
        //driver.findElement(By.xpath("(//a[@class='view-content']/div)[2]/div/div/h2")).click();
        driver.findElement(By.xpath("//h2/a[@href='/fr_FR/event/kawasaki']")).click();
        Thread.sleep(3000);
        System.out.println("test : " + driver.getCurrentUrl());
        assertThat(driver.getCurrentUrl(), containsString("https://auth.tesla.com/"));

        // -------------------- //

    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
