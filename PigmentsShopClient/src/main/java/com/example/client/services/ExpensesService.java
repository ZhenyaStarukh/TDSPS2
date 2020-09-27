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
            if(color.getWeight() < 300.0){
                expenses += (1000-color.getWeight()) * color.getPrice();
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


        if(expenses > income) shop.setPillow(shop.getPillow() - (expenses-income));
        else{
            income -= expenses;
            shop.setPillow(shop.getPillow() + income);
        }

        System.out.println("Pillow: "+decFormat.format(shop.getPillow()));
    }
}
