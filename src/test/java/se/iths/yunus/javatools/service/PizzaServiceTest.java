package se.iths.yunus.javatools.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.yunus.javatools.exception.InvalidPizzaException;
import se.iths.yunus.javatools.exception.NoPizzaFoundException;
import se.iths.yunus.javatools.model.Pizza;
import se.iths.yunus.javatools.repository.PizzaRepository;
import se.iths.yunus.javatools.validator.PizzaValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private PizzaValidator pizzaValidator;

    @InjectMocks
    private PizzaService pizzaService;
    private Pizza pizza;

    @BeforeEach
    public void setUp() {
        pizza = new Pizza(1L, "Hawaii", "Tomat", "Ja", 99);
    }


    // arrange act assert


    @Test
    void getAllPizza() {
        when(pizzaRepository.findAll()).thenReturn(List.of(pizza));

        List<Pizza> result = pizzaService.getAllPizza();

        assertEquals(1, result.size());
        assertEquals(pizza, result.get(0));

        verify(pizzaRepository).findAll();


    }

    @Test
    void getPizza() {
        //mockar
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));

        // kör min service metod
        Pizza result = pizzaService.getPizza(1L);

        // assertions
        assertEquals(pizza, result);
        assertEquals(1L, result.getId());

        verify(pizzaRepository).findById(1L);

    }

    @Test
    void createPizza() {
        Pizza pizza = new Pizza(1L, "Hawaii", "Tomat", "Ja", 99);

        when(pizzaRepository.save(pizza)).thenReturn(pizza);

        Pizza result = pizzaService.createPizza(pizza);

        assertEquals(pizza, result);
        assertEquals("Hawaii", result.getName());

        verify(pizzaValidator).validate(pizza);
        verify(pizzaRepository).save(pizza);

    }

    @Test
    void updatePizza() {
        // pizzan som finns
        Pizza existing = new Pizza();
        existing.setId(1L);
        existing.setName("Kebab");
        existing.setTopping("Tomat");
        existing.setCheese("Ja");
        existing.setPrice(149);

        // nya uppdaterad pizzan
        Pizza updated = new Pizza();
        updated.setName("Kebab");
        updated.setTopping("Tomat");
        updated.setCheese("Ja");
        updated.setPrice(149);

        //mockar
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(pizzaRepository.save(existing)).thenReturn(existing);

        // kör service
        Pizza result = pizzaService.updatePizza(1L, updated);

        //assertions
        assertEquals("Kebab", result.getName());
        assertEquals(149, result.getPrice());

        // verifierar
        verify(pizzaValidator).validate(updated);
        verify(pizzaRepository).findById(1L);
        verify(pizzaRepository).save(existing);
    }

    @Test
    void deletePizza() {
        Pizza existing = new Pizza();
        existing.setId(1L);

        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(existing));

        pizzaService.deletePizza(1L);

        verify(pizzaRepository).findById(1L);
        verify(pizzaRepository).deleteById(1L);

    }

    // exceptions tester

    @Test
    void getPizzaExceptionWhenNotFound() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoPizzaFoundException.class, () ->
                pizzaService.getPizza(1L));

        verify(pizzaRepository).findById(1L);
    }

    @Test
    void updatePizzaWhenNotFound() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());

        Pizza updated = new Pizza();

        assertThrows(NoPizzaFoundException.class, () ->
                pizzaService.updatePizza(1L, updated));

        verify(pizzaRepository).findById(1L);

    }

    @Test
    void createPizzawhenInvalid() {
        doThrow(new InvalidPizzaException("Invalid price")).when(
                pizzaValidator).validate(pizza);

        assertThrows(InvalidPizzaException.class, () ->
                pizzaService.createPizza(pizza));

        verify(pizzaRepository, never()).save(any());


    }
}
