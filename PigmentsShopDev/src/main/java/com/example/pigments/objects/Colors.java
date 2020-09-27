package com.example.pigments.objects;

import javax.persistence.Entity;
import javax.persistence.*;


@Entity
@Table(name = "colors")
public class Colors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(precision = 12, scale = 2)
    private double price;
    private double weight;


    public Colors() {}

    public Colors(double price, double weight, String name) {
        this.price = price;
        this.weight = weight;
        this.name = name;
    }

    public String getName() { return name;}

    public double getPrice() { return price;}

    public double getWeight() { return weight;}

    public void setWeight(double weight) { this.weight = weight;}

    @Override
    public String toString() { return name+" "+ price +"  "+ weight;}
}
