package com.example.pigments.objects;

import javax.persistence.*;


@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 12, scale = 2)
    private double pillow;


    public Shop(){}

    public double getPillow() { return pillow;}

    public void setPillow(double pillow) { this.pillow = pillow; }

}
