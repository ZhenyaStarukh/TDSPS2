package com.example.client.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class Order {


    private final ArrayList<Pigment> orderList = new ArrayList<>();
    private final String clientId;
    private double totalPrice;


    public Order(String clientId)
    {
        this.clientId=clientId;
    }



    public void addPigment(Pigment pigment){orderList.add(pigment);}


    public void alterPigmentFromOrder(int index, double[] array, List<Colors> colors){
        orderList.get(index-1).alterFormula(array,colors);
    }


    public void addEffectForPigment(int index, int effect){
        orderList.get(index-1).addEffect(effect);
    }


    public Pigment getPigment(int index){return orderList.get(index);}


    public void showOrder()
    {
        for(Pigment pigment : orderList){
            System.out.println(pigment);
        }
    }


    public String toString(List<Colors> colors)
    {
        String order = "Order:\n";
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        for(int i = 0;i < orderList.size();i++){
            order += (i + 1)
                    +") "
                    +orderList.get(i).getName()+"  ";

            if(orderList.get(i).haveEffects())
                order += "("+orderList.get(i).getEffects()+")  ";

            order += decimalFormat.format(orderList.get(i).totalPrice(colors))+" UAH    "
                    + decimalFormat.format(orderList.get(i).getWeight())+"g\n";
        }

        return order;

    }



    public void removePigment(int index) throws IndexOutOfBoundsException
    {
            orderList.remove(index-1);
            System.out.println("PIGMENT IS REMOVED.");
    }

    public double countPigment(int index)
    {
        double count = 0.0;

        for (Pigment pigment : orderList)
        {
            count += pigment.getWeight() * pigment.getFormula(index);
        }
        return count;
    }

    public String getId()
    {
        return clientId;
    }

    public void calculateTotalPrice(List<Colors> colors)
    {
        totalPrice = 0.0;

        for (Pigment pigment : orderList) {
            totalPrice += pigment.totalPrice(colors);
        }
    }
    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setDiscount()
    {
        totalPrice -= totalPrice*0.05;
        System.out.println("DISCOUNT ADDED");
    }

    public void showTotal(List<Colors> colors)
    {
        System.out.println(this.toString(colors));

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        System.out.println("___________________________________________");

        System.out.println("Total price:\t\t\t\t\t"+decimalFormat.format(totalPrice)+" UAH");
    }
}
