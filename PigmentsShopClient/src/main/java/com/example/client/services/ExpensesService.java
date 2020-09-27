package com.example.client.services;

import com.example.client.objects.Colors;
import com.example.client.objects.Shop;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.List;



@Service
public final class ExpensesService {

    public static double buyPigments(List<Colors> colors){
        double expenses = 0.0;
        for(Colors color: colors){
            double colorWeight = color.getWeight();
            double colorPrice = color.getPrice();

            if(colorWeight < 300.0){
                expenses += (1000-colorWeight) * colorPrice;
                color.setWeight(1000.0);
            }
        }

        return expenses;
    }


    public static void addToPillow(double income, double expenses, Shop shop){
        DecimalFormat decFormat = new DecimalFormat("#,##0.00");

        System.out.println("Expenses: "+decFormat.format(expenses)+"\nIncome: "+decFormat.format(income)+"\nPillow: "
                +decFormat.format(shop.getPillow()));
        System.out.println("_______________\nResult:");

        double pillow = shop.getPillow();

        if(expenses > income) shop.setPillow(pillow - (expenses-income));
        else{
            income -= expenses;
            shop.setPillow(pillow + income);
        }

        System.out.println("Pillow: "+decFormat.format(shop.getPillow()));
    }
}
