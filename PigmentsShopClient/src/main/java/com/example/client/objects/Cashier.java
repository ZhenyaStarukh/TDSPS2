package com.example.client.objects;



public final class Cashier {
    private  double currentBalance = 0.00;

    public  void addToCashier(double sum){
        currentBalance += sum;
    }

    public double getCurrentBalance(){
        return currentBalance;
    }

    public void resetCurrentBalance(){
        currentBalance=0.0;
    }
}
