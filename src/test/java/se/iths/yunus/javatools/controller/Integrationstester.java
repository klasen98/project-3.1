package se.iths.yunus.javatools.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import se.iths.yunus.javatools.model.Pizza;
import se.iths.yunus.javatools.repository.PizzaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //Startar hela applikationen
@AutoConfigureMockMvc
@Transactional // rensar databasen inför varje test
public class Integrationstester {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PizzaRepository pizzaRepository;

    private Pizza pizza;

    @BeforeEach
    void setUp() {
        pizza = new Pizza(null, "Hawaii", "Tomat", "Ja", 99);
    }

    //Skapa en entitet och verifiera att den sparas
    @Test
    void testCreatePizza() throws Exception {
        // testar post /pizzas
        mockMvc.perform(post("/pizzas")
                        .param("name", "Hawaii")
                        .param("topping", "Skinka & Ananas")
                        .param("cheese", "Ja")
                        .param("price", "89"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pizzas"));

        // Verifiera att pizzan faktiskt finns i databasen
        var pizzas = pizzaRepository.findAll();
        assertThat(pizzas).hasSize(1);
        assertThat(pizzas.get(0).getName()).isEqualTo("Hawaii");
    }

    // Hämta en entitet
    @Test
    void testGetPizzaById() throws Exception {
        // Skapa först i databasen
        Pizza saved = pizzaRepository.save(pizza);

        // Act
        mockMvc.perform(get("/pizzas/" + saved.getId()))
                .andExpect(status().isOk());

        // Assert
        assertThat(pizzaRepository.findById(saved.getId())).isPresent();
    }

    // Hämta alla entiteter
    @Test
    void testGetAllPizzas() throws Exception {
        // Skapa flera entiteter i databasen först
        pizzaRepository.save(pizza);
        pizzaRepository.save(new Pizza(null, "Vesuvio", "Skinka", "Ja", 99));

        // Act
        mockMvc.perform(get("/pizzas"))
                .andExpect(status().isOk());

        // Assert
        assertThat(pizzaRepository.count()).isEqualTo(2);
    }
}