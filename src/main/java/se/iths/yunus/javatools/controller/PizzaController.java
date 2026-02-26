package se.iths.yunus.javatools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.yunus.javatools.model.Pizza;
import se.iths.yunus.javatools.service.PizzaService;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    // dependency injection
    private final PizzaService pizzaService;

    //konstruktor
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    // hämtar alla pizzor
    @GetMapping
    public String getAllPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllPizza());
        return "pizzas";

    }

    // hämtar 1 pizza via id
    @GetMapping("/{id}")
    public String getPizza(@PathVariable Long id, Model model) {
        model.addAttribute("pizza", pizzaService.getPizza(id));
        return "pizza";

    }

    @GetMapping("new")
    public String visaCreatePizza(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "create-pizza";
    }


    @PostMapping
    public String createPizza(@ModelAttribute Pizza pizza) {
        pizzaService.createPizza(pizza);

        return "redirect:/pizzas";
        
    }

    @PutMapping("/{id}")
    public String updatePizza(@PathVariable Long id, @ModelAttribute Pizza pizza) {
        pizzaService.updatePizza(id, pizza);
        return "redirect:/pizzas";
    }

    @DeleteMapping("/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pizza", pizzaService.getPizza(id));
        return "edit-pizza";
    }


}
