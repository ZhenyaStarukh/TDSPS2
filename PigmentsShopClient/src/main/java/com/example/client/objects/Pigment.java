package com.example.client.objects;

import org.apache.commons.math3.util.Precision;
import java.util.*;



public class Pigment implements Cloneable{

    private Integer id;
    private String name;
    private String creatorPhone;
    private double price;
    private double cyan, magenta, yellow, black, white;
    private String effects;
    private double weight;

    private void resetEffects(){ effects="None";}

    public Pigment(){}

    public Pigment(String number, double[] array, List<Colors> colors){
        createFormula(array);
        creatorPhone = number;
        resetEffects();
        pricePerGram(colors);
    }

//Getters and setters-------------------------------------------------------------------------------------------------
    public Integer getId() { return id;}

    public void setId(Integer id) { this.id = id;}

    public void setName(String name) { this.name = name;}

    public String getName() {
        if (name == null) return Arrays.toString(getArray());
        return name;
    }

    public void setCreatorPhone(String creatorPhone) { this.creatorPhone = creatorPhone;}

    public String getCreatorPhone(){ return creatorPhone;}

    public void setPrice(double price) { this.price = price;}

    public double getPrice() {return price;}

    public double getCyan() { return cyan;}

    public void setCyan(double cyan) { this.cyan = cyan;}

    public double getMagenta() { return magenta;}

    public void setMagenta(double magenta) { this.magenta = magenta;}

    public double getYellow() { return yellow;}

    public void setYellow(double yellow) { this.yellow = yellow;}

    public double getBlack() { return black;}

    public void setBlack(double black) { this.black = black;}

    public double getWhite() { return white;}

    public void setWhite(double white) { this.white = white;}

    public void setEffects(String effects) { this.effects = effects;}

    public void setWeight(double weight){
        this.weight = weight;
    }

    public double getWeight(){return weight;}


    public double[] getArray(){
        double[] array = new double[5];
        array[0]=cyan;
        array[1]=magenta;
        array[2]=yellow;
        array[3]=black;
        array[4]=white;
        return array;
    }

    public void setPigments(double[] array){
        cyan=array[0];
        magenta=array[1];
        yellow=array[2];
        black=array[3];
        white=array[4];
    }

//--------------------------------------------------------------------------------------------------------------------
    public Pigment clone(List<Colors> colors)
    {
        return new Pigment(this.creatorPhone, this.getArray(),colors);
    }

    @Override
    public String toString()
    {
        return name+" "+getFormula()+" "+ " "+ Precision.round(price,2);
    }


//Effects--------------------------------------------------------------------------------------------------------
    public String getEffects(){
        return effects;
    }

    public boolean haveEffects(){
        return !effects.equals("None");
    }


    public void addEffect(int index)
    {
        String effectToAdd = Effect.values()[index-1].getName();

        if(!haveEffects()) effects = effectToAdd;

        else effects+=", "+effectToAdd;
        System.out.println(effectToAdd+" added.");
    }

//Formula---------------------------------------------------------------------------------------------------------

    public boolean rightFormula(double[] array) throws IllegalArgumentException
    {
        double sum = Arrays.stream(array).sum();

        for(double d : array)
        {
            if (d > 1 || d < 0 ) throw  new IllegalArgumentException("Percentage can't be more than 1 or less than 0.");
            if (sum > 1) throw new IllegalArgumentException("Total percentage sum should be less or equal than 1.");
        }

        return true;
    }

    private void createFormula(double[] array)
    {
        setPigments(new double[]{0,0,0,0,0});
        if(rightFormula(array)) setPigments(array);
    }

    public void alterFormula(double[] array, List<Colors> colors)
    {
        createFormula(array);
        pricePerGram(colors);
    }

    public String getFormula()
    {
        String str = "| "+ Precision.round(cyan, 3) + ", "
                + Precision.round(magenta, 3) + ", "
                + Precision.round(yellow, 3) + ", "
                + Precision.round(black, 3) + ", "
                + Precision.round(white, 3) +" | ";
        if(!effects.equals("None")) str += " "+effects;
        return str;
    }

    public double getFormula(int index)
    {
        double[] formula = getArray();
        return formula[index];
    }


//Price------------------------------------------------------------------------------------------------------------
    private void splitAndAdd(ArrayList<String> array)
    {
        if (!haveEffects()) return;

        String[] strings = effects.split(", ");
        Collections.addAll(array, strings);
    }


    public void pricePerGram(List<Colors> colors)
    {
       ArrayList<String> effectsArray = new ArrayList<>();
       splitAndAdd(effectsArray);

        price = 0.0;
        double[] formula = getArray();


        for(int i = 0;i < formula.length;i++){
            price += colors.get(i).getPrice()  * formula[i];
        }

        if(!effectsArray.isEmpty())
        for (String effect : effectsArray) {
            price += Effect.valueOf(effect.toUpperCase())
                    .getPrice();
        }
    }


    public double totalPrice(List<Colors> colors){
        pricePerGram(colors);
        return price * weight;
    }

}
