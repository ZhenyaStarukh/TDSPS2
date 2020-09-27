package com.example.pigments.objects;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name="client")
public final class Client {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "phone_number",length = 13, unique = true)
    private String phoneNumber;

    @Column(length = 50)
    private String name;


    public Client(){ phoneNumber = "None";}

    public Client(String name, String phoneNumber)
    {
        this.name = name;
        setPhoneNumber(phoneNumber);
    }

    public UUID getId() { return id;}

    public void setId(UUID id) { this.id = id;}

    public void setName(String name) { this.name = name;}

    public String getName() { return name;}

    public String getPhoneNumber() { return phoneNumber;}

    private boolean checkNumber(String number){ return number.matches("(\\+380)[3-9]\\d{8}");}


    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException
    {
        if (!checkNumber(phoneNumber))
            throw new IllegalArgumentException("Please enter the real phone number (e.g. +380961234567). "+this.phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() { return phoneNumber + " " + name;}
}
