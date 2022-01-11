package com.openclassrooms.testing;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private static Instant startedAt;
    private Calculator calculatorUnderTest;

    @BeforeAll
    public static void initSartingTime() {
        System.out.println("Appel avant tous les tests");
        startedAt = Instant.now();
    }

    @AfterAll
    public static void showTestDuration() {
        System.out.println("Appel après tous les test");
        Instant endedAt = Instant.now();
        long duration = Duration.between(startedAt, endedAt).toMillis();
        System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
    }

    @BeforeEach
    public void initCalculator() {
        calculatorUnderTest = new Calculator();
        System.out.println("Appel avant chaque test");
    }

    @AfterEach
    public void undefCalculator() {
        System.out.println("Appel apès chaque test");
        calculatorUnderTest = null;
    }

    @Test
    void testAddTwoPositiveNumbers() {
        // Arrange
        int a = 2;
        int b = 3;


        // Act
        int somme = this.calculatorUnderTest.add(a, b);

        // Assert
        assertEquals(5, somme);
        // Assert avec AssertJ
        assertThat(somme).isEqualTo(5);
    }

    @Test
    void testMultiplyTwoPositiveumber() {
        // Arrange
        int a = 2;
        int b = 3;


        // Act
        int produit = this.calculatorUnderTest.multiply(a, b);

        // Assert
        assertEquals(6, produit);
        // Assert avec AssertJ
        assertThat(produit).isEqualTo(6);
    }


    /**
     * @ParameterizedTest et @ValueSource fonctionnent en couple.
     * S'il manque l'une des deux annotation ça ne fonctionne pas
     * @ParameterizedTest : Permet de donner un titre au test que l'on va faire
     * fonctionne également sans le paramètre name
     * @ValueSource : S'ocupe de gerer les différents paramètres
     */
    @ParameterizedTest(name = "{0} x 0 doit être égal à 0")
    @ValueSource(ints = {1, 2, 42, 1011, 5089})
    public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
        // Arrange -- Tout est prêt !

        // Act -- Multiplier par zéro
        int actualResult = calculatorUnderTest.multiply(arg, 0);

        // Assert -- ça vaut toujours zéro !
        assertEquals(0, actualResult);

        // Assert avec AssertJ
        assertThat(actualResult).isEqualTo(0);
    }


    @ParameterizedTest(name = "{0} + {1} should equal to {2}")
    @CsvSource({"1,1,2", "2,3,5", "42,57,99"})
    public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
        // Arrange -- Tout est prêt !

        // Act
        int actualResult = calculatorUnderTest.add(arg1, arg2);

        // Assert
        assertEquals(expectResult, actualResult);
        // Assert avec AssertJ
        assertThat(actualResult).isEqualTo(expectResult);
    }


    @Timeout(1)
    @Test
    public void longCalcul_shouldComputeInLessThan1Second() {
        // Arrange

        // Act
        calculatorUnderTest.longCalculation();

        // Assert
        // ...
    }


    @Test
    public void digitsSet_shouldReturnsTheSetOfDigits_ofPositiveInteger() {
        // GIVEN
        int number = 95897;

        // WHEN
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

        // THEN

        // Juste en Junit
        Set<Integer> expectedDigits = Stream.of(5, 7, 8, 9).collect(Collectors.toSet());
        assertEquals(expectedDigits, actualDigits);

        // AssertJ
        assertThat(actualDigits).containsExactlyInAnyOrder(5, 7, 8, 9);

    }

}
