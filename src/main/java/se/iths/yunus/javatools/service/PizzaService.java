package se.iths.yunus.javatools.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.iths.yunus.javatools.exception.NoPizzaFoundException;
import se.iths.yunus.javatools.model.Pizza;
import se.iths.yunus.javatools.repository.PizzaRepository;
import se.iths.yunus.javatools.validator.PizzaValidator;

import java.util.List;

@Service
public class PizzaService {

    // skriver till loggfil istället för konsolen
    private static final Logger log = LoggerFactory.getLogger(PizzaService.class);

    private final PizzaRepository pizzaRepository;
    private final PizzaValidator pizzaValidator;

    public PizzaService(PizzaRepository pizzaRepository, PizzaValidator pizzaValidator) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaValidator = pizzaValidator;
    }

    public List<Pizza> getAllPizza() {
        log.info("Hämtar alla pizzor");
        return pizzaRepository.findAll();
    }

    public Pizza getPizza(Long id) {
        log.info("Hämtar pizza med id: " + id);
        return pizzaRepository.findById(id).orElseThrow(() -> {
            log.warn("Ingen pizza hittades med id: " + id);
            return new NoPizzaFoundException("Ingen pizza hittades med id " + id);
        });
    }

    public Pizza createPizza(Pizza pizza) {
        log.info("Skapar ny pizza: " + pizza.getName());
        pizzaValidator.validate(pizza);
        Pizza saved = pizzaRepository.save(pizza);
        log.info("Pizza skapad med id: " + saved.getId());
        return saved;
    }

    public Pizza updatePizza(Long id, Pizza pizza) {
        log.info("Uppdaterar pizza med id: " + id);
        Pizza existing = pizzaRepository.findById(id).orElseThrow(() -> {
            log.warn("Ingen pizza hittades med id: " + id);
            return new NoPizzaFoundException("Ingen pizza hittades med id " + id);
        });

        pizzaValidator.validate(pizza);

        existing.setName(pizza.getName());
        existing.setTopping(pizza.getTopping());
        existing.setCheese(pizza.getCheese());
        existing.setPrice(pizza.getPrice());

        Pizza updated = pizzaRepository.save(existing);
        log.info("Pizza med id " + updated.getId() + " uppdaterad");
        return updated;
    }

    public void deletePizza(Long id) {
        log.info("Tar bort pizza med id: " + id);
        Pizza existing = pizzaRepository.findById(id).orElseThrow(() -> {
            log.warn("Ingen pizza hittades med id: " + id);
            return new NoPizzaFoundException("Ingen pizza med id " + id);
        });
        pizzaRepository.deleteById(existing.getId());
        log.info("Pizza med id " + existing.getId() + " borttagen");
    }
}