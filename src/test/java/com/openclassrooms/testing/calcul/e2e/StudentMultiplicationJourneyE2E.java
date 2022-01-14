package com.openclassrooms.testing.calcul.e2e;

import com.openclassrooms.testing.calcul.e2e.page.CalculatorPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
// Indique que l'on lance l'application comme un environement de serveur web.
// Avec Ramdom spring va choisir un port hau hazard il va quand même evité les conflits de port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentMultiplicationJourneyE2E {

    // Spring va initialiser la variable port avec le numéro du port qu'il a choisi aléatoirement
    @LocalServerPort
    private Integer port;
    private WebDriver webDriver = null;
    private String baseUrl;

    /**
     * Avant tout les tests il nous faut un driver de navigateur.
     * Il est important d'avoir le naviateur d'installer sur le PC.
     * WebDriverManager ne télécharge que le Driver
     */
    @BeforeAll
    public static void setUpFirefoxDriver(){
        WebDriverManager.firefoxdriver().setup();
    }

    /**
     * Avant chaque test on ouvre le navigateur
     */
    @BeforeEach
    public void setUpWebDriver(){
        webDriver = new FirefoxDriver();
        baseUrl = "http://localhost:"+ port + "/calculator";
    }

    /**
     * On ferme le navigateur après chaque test
     */
    @AfterEach
    public void quitWebDriver(){
        if(webDriver != null){
            webDriver.quit();
        }
    }


    @Test
    public void aStudenUsesTheCalculatorToMultiplyTwoBySixteen(){

        // GIVEN

        /**
         * On récupère les élément du Template grace aux ID
         */
        webDriver.get(baseUrl);
        WebElement leftField = webDriver.findElement(By.id("left"));
        WebElement rightField = webDriver.findElement(By.id("right"));
        WebElement typeDropDown = webDriver.findElement(By.id("type"));
        WebElement submitButton = webDriver.findElement(By.id("submit"));

        // WHEN

        /**
         * On modifie la valeur des champs et on ajoute le clic sur le bouton submit
         */
        leftField.sendKeys("2");
        rightField.sendKeys("16");
        typeDropDown.sendKeys("x");
        submitButton.submit();

        // THEN

        /**
         * On laisse le temps au navigateur de faire le taf
         */
        final WebDriverWait waiter = new WebDriverWait(webDriver, 5);
        /**
         * On vérifie que le bloc solution est bien présent. Normalement arpès la multiplication
         * il doit apparaitre
         */
        WebElement solutionElement = waiter.until(
                ExpectedConditions.presenceOfElementLocated(By.id("solution")));
        /**
         * On test la valeur de la solution
         */
        String solution = solutionElement.getText();
        assertThat(solution).isEqualTo("32");
    }


    /**
     * Cette méthode fait la même chose que le test précédent. Elle utlise la classe CalculatorPage
     * C'est plus simple.
     * Si la page HTML change on devra le changer que dans la classe CalculatorPage et non modifier chacune
     * des méthode de test ici.
     *
     * Exemple: si l'id du type de calcul change sur la page HTML.Il faut juste le mettre à jour dans la classe
     * CalculatorPage.
     *
     * Sans la classe CalculatorPAge il faut le faire sur chacun des des ou nous trouvons l'instruction
     * WebElement typeDropDown = webDriver.findElement(By.id("type"));
     *
     */
    @Test
    public void aStudentUsesTheCalculatorToMultiplyTwoBySixteen() {
        // GIVEN
        webDriver.get(baseUrl);
        final CalculatorPage calculatorPage = new CalculatorPage(webDriver);

        // WHEN
        final String solution = calculatorPage.multiply("2", "16");

        // THEN
        assertThat(solution).isEqualTo("32"); // 2 x 16

    }


    @Test
    public void aStudentUsesTheCalculatorToAddTwoToSixteen() throws InterruptedException {
        // GIVEN
        webDriver.get(baseUrl);
        final CalculatorPage calculatorPage = new CalculatorPage(webDriver);

        // WHEN
        final String solution = calculatorPage.add("2", "16");

        // THEN
        assertThat(solution).isEqualTo("18"); // 2 + 16
    }



}
