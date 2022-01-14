package com.openclassrooms.testing.calcul.controller;

import com.openclassrooms.testing.calcul.domain.Calculator;
import com.openclassrooms.testing.calcul.service.CalculatorService;
import com.openclassrooms.testing.calcul.service.SolutionFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Nouvauté il faut cette annottion car avec spring on va pas faire les new CalculatorController ()
 * Spring le fait déjà pour nous.
 */
@WebMvcTest(controllers = {CalculatorController.class, CalculatorService.class})
/**
 * Classique on ajoute l'extention Spring
 */
@ExtendWith(SpringExtension.class)
public class CalculatorControllerSIT {

    /**
     * @Inject c'est la même chose que @Autowired.
     * @Inject est plus générique que @Autowired qui est plus spécifique au framework spring
     *
     * Avec @Inject le code serrait plus facilement migré sur un autre Framework de Spring
     */
    @Inject
    private MockMvc mockMvc;

    /**
     * La classe MockMvc appellera votre contrôleur comme si l’application avait réellement été démarrée,
     * 	et vous permet d’inspecter la façon dont elle répond dans vos tests.
     */


    /**
     * @Mock permet de nous faire un mock
     */
    @MockBean
    private SolutionFormatter solutionFormatter;

    @MockBean
    private Calculator calculator;


    /**
     * Remarque on est pas obliger de remettre CalculatorController car il est déjà en haut
     */
    @Autowired
    private CalculatorController calculatorControllerUnderTest;

    @Test
    public void givenAUser_whenRequestIsMadeToAdd_thenASolutionSouldBeShown() throws Exception {
        /**
         * Avec les test d'intégration système on peut mocker une partie de l'application.
         * Pour ne tester que les intéractions qui nous conviennent.
         */
        when(calculator.add(2,3)).thenReturn(5);


        // mockMvc simule un navigateur internet
        mockMvc.perform(post("/calculator")
                        .param("leftArgument", "2")
                        .param("rightArgument", "3")
                        .param("calculationType", "ADDITION")
                ).andExpect(status().is2xxSuccessful()).
                andExpect(content().string(containsString("id=\"solution\""))).
                andExpect(content().string(containsString(">5</span>")));


        verify(calculator).add(2, 3);
        verify(solutionFormatter).format(5);
    }
}
