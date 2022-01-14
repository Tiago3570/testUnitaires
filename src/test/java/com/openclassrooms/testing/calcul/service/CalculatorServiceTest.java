package com.openclassrooms.testing.calcul.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.testing.calcul.domain.Calculator;
import com.openclassrooms.testing.calcul.domain.model.CalculationModel;
import com.openclassrooms.testing.calcul.domain.model.CalculationType;

// On ajoute mockito
@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

	@Mock
	Calculator calculator;

	@Mock
	SolutionFormatter solutionFormatter;

	CalculatorService classUnderTest;

	@BeforeEach
	public void init() {
		classUnderTest = new CalculatorServiceImpl(calculator, solutionFormatter);
	}

	@Test
	public void calculate_shouldUseCalculator_forAddition() {
		// GIVEN
		when(calculator.add(1, 2)).thenReturn(3);

		// WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 1, 2)).getSolution();

		// THEN
		// On vérifie qui la méthode add du calculator à bien été appélé avec les paramètres 1 et 2
		verify(calculator).add(1, 2);
		assertThat(result).isEqualTo(3);
	}

	@Test
	public void calculate_shouldUseCalculator_forSubstraction() {
		// GIVEN
		when(calculator.sub(3, 2)).thenReturn(1);

		// WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.SUBTRACTION, 3, 2))
				.getSolution();

		// THEN
		verify(calculator).sub(3, 2);
		assertThat(result).isEqualTo(1);
	}

	@Test
	public void calculate_shouldUseCalculator_forMultiplication() {
		// GIVEN
		when(calculator.multiply(-3, 2)).thenReturn(-6);

		// WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.MULTIPLICATION, -3, 2))
				.getSolution();

		// THEN
		verify(calculator).multiply(-3, 2);
		assertThat(result).isEqualTo(-6);
	}

	@Test
	public void calculate_shouldUseCalculator_forDivision() {
		// GIVEN
		when(calculator.divide(6, 3)).thenReturn(2);

		// WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.DIVISION, 6, 3))
				.getSolution();

		// THEN
		verify(calculator).divide(6, 3);
		assertThat(result).isEqualTo(2);
	}

	@Test
	public void calculate_shouldUseCalculator_forAnyAddition() {
		// GIVEN
		final Random r = new Random();
		// Avec mockito on est pas obliger de mettre des vrais valeurs on pet mettre directement des type
		// Attention il faut également adapter le verifiy en question
		when(calculator.add(any(Integer.class), any(Integer.class))).thenReturn(3);

		// WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, r.nextInt(), r.nextInt())).getSolution();

		// THEN
		// On vérifie que la méthode addition a été appelé 1 seul fois
		verify(calculator, times(1)).add(any(Integer.class), any(Integer.class));
		// On vérifie que la méthode substraction n'a jamais été appelé
		verify(calculator, never()).sub(any(Integer.class), any(Integer.class));
		assertThat(result).isEqualTo(3);
	}

	@Test
	public void calculate_shouldThrowIllegalArgumentAxception_forADivisionBy0() {
		// GIVEN
		// On test les exceptions   on remplace thenReturn par thenThrow
		when(calculator.divide(1, 0)).thenThrow(new ArithmeticException());

		// WHEN
		// ON vérifie que le service nous retourne bien une IllegalArgumentException.
		// Normalement quand une ArithmeticException se produit le service doit la transformer en une IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> classUnderTest.calculate(
				new CalculationModel(CalculationType.DIVISION, 1, 0)));

		// THEN
		verify(calculator, times(1)).divide(1, 0);
	}

	@Test
	public void calculate_shouldFormatSolution_forAnAddition() {
		// GIVEN
		when(calculator.add(10000, 3000)).thenReturn(13000);
		when(solutionFormatter.format(13000)).thenReturn("13 000");

		// WHEN
		final String formattedResult = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 10000, 3000)).getFormattedSolution();

		// THEN
		assertThat(formattedResult).isEqualTo("13 000");
	}

}
