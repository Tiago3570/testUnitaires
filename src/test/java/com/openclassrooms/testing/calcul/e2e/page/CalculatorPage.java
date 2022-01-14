package com.openclassrooms.testing.calcul.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Dans cette classe on déclare l'ensemble des éléments que l'on peut trouver sur la page calculator.html
 * Chaque élément doit être une instance de WebElement
 */
public class CalculatorPage {


    public static final String ADDITION_SYMBOL = "+";
    public static final String SUBTRACTION_SYMBOL = "-";
    public static final String DIVISION_SYMBOL = "/";
    public static final String MULTIPLICATION_SYMBOL = "x";

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "left")
    private WebElement leftArgument;

    @FindBy(id = "right")
    private WebElement rightArgument;

    @FindBy(id = "type")
    private WebElement calculationType;

    @FindBy(id = "solution")
    private WebElement solution;

    private final WebDriver webDriver;


    /**
     * PageFactory: permet d'initialiser l'ensemble des attributs @FindBy
     */
    public CalculatorPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    /**
     * On ajoute une méthode permetant de soumettre le calcul
     */
    private String calculate(String calculationTypeValue, String leftValue, String rightValue) {
        leftArgument.sendKeys(leftValue);
        calculationType.sendKeys(calculationTypeValue);
        rightArgument.sendKeys(rightValue);
        submitButton.click();

        final WebDriverWait waiter = new WebDriverWait(webDriver, 5);
        waiter.until(ExpectedConditions.visibilityOf(solution));

        return solution.getText();
    }


    /**
     * On y ajoute 4 méthodes pour faciliter les utilisateurs de cette classe pour les 4 opérations courantes.
     * Elles appelleront toutes la méthode calculate
     */
    public String add(String leftValue, String rightValue) {
        return calculate(ADDITION_SYMBOL, leftValue, rightValue);
    }

    public String subtract(String leftValue, String rightValue) {
        return calculate(SUBTRACTION_SYMBOL, leftValue, rightValue);
    }

    public String divide(String leftValue, String rightValue) {
        return calculate(DIVISION_SYMBOL, leftValue, rightValue);
    }

    public String multiply(String leftValue, String rightValue) {
        return calculate(MULTIPLICATION_SYMBOL, leftValue, rightValue);
    }
}
