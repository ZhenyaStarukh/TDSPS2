package com.example.client.objects;

import java.util.UUID;



public final class Client {


    private UUID id;
    private String phoneNumber;
    private String name;

    public Client(){ phoneNumber = "None";}

    public Client(String name, String phoneNumber){
        this.name = name;
        setPhoneNumber(phoneNumber);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException
    {
        if (!checkNumber(phoneNumber))
            throw new IllegalArgumentException("Please enter the real phone number (e.g. +380961234567)." +this.phoneNumber);
        this.phoneNumber = phoneNumber;
    }


    private boolean checkNumber(String number){
        return number.matches("(\\+380)[3-9]\\d{8}");
    }

    public void resetId(){
        phoneNumber = "None";
    }

    @Override
    public String toString() {
        return phoneNumber + " " + name;
    }
}
