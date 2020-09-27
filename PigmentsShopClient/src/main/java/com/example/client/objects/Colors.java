package com.example.client.objects;


public class Colors {


    private Integer id;
    private String name;
    private double price;
    private double weight;

    public Colors() {}

    public Colors(double price, double weight, String name) {
        this.price = price;
        this.weight = weight;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public void reduceWeight(double weight){
        if (weight <= this.weight)
            this.weight -= weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name+" "+ price +"  "+ weight;
    }
}
