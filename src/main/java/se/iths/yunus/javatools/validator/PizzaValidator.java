package se.iths.yunus.javatools.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.iths.yunus.javatools.exception.InvalidPizzaException;
import se.iths.yunus.javatools.model.Pizza;

@Component
public class PizzaValidator {

    private static final Logger log = LoggerFactory.getLogger(PizzaValidator.class);

    public void validate(Pizza pizza) {
        if (pizza == null) {
            log.error("Validering misslyckades: pizza är null");
            throw new InvalidPizzaException("Pizza cannot be null");
        }
        validateName(pizza.getName());
        validateTopping(pizza.getTopping());
        validateCheese(pizza.getCheese());
        validatePrice(pizza.getPrice());
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            log.error("Validering misslyckades: namn kan inte vara tomt");
            throw new InvalidPizzaException("Pizza namn kan inte vara tomt");
        }
        log.info("Validering OK: namn '" + name);
    }

    private void validateTopping(String topping) {
        if (topping == null || topping.isBlank()) {
            log.error("Validering misslyckades: topping kan inte vara tomt");
            throw new InvalidPizzaException("Topping kan inte vara tomt");
        }
        log.info("Validering OK: topping '" + topping);
    }

    private void validateCheese(String cheese) {
        if (cheese == null || cheese.isBlank()) {
            log.error("Validering misslyckades: ost kan inte vara tomt");
            throw new InvalidPizzaException("Ost kan inte vara tomt");
        }
        log.info("Validering OK: ost '" + cheese);
    }

    private void validatePrice(int price) {
        if (price < 0) {
            log.error("Validering misslyckades: pris kan inte vara negativt");
            throw new InvalidPizzaException("Pris kan inte vara negativt");
        }
        log.info("Validering OK: pris " + price);
    }
}