package se.iths.yunus.javatools.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pizza")
public class Pizza {

    //primärnyckel
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // tom konstruktor JPA
    public Pizza() {
    }

    // attribut
    private String name;
    private String topping;
    private String cheese;
    private int price;

    // konstruktorer
    public Pizza(Long id, String name, String topping, String cheese, int price) {
        this.id = id;
        this.name = name;
        this.topping = topping;
        this.cheese = cheese;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getCheese() {
        return cheese;
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topping='" + topping + '\'' +
                ", cheese='" + cheese + '\'' +
                ", price=" + price +
                '}';
    }
}




