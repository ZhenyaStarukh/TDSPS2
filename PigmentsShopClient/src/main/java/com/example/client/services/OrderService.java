package com.example.client.services;

import com.example.client.objects.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;



public class OrderService {

    private static final String address = "http://localhost:8080/pigments";

    public static String encodeNumber(String number)
    {
        return number.replace("+","%2B");
    }


    public static Order makeOrder(Client client)
    {
        String clientName = client.getName();

        System.out.println(clientName.toUpperCase()+"'S ORDER CREATED.");
        return new Order(client.getPhoneNumber());
    }


    private static List<Pigment> getPigmentsList(String phoneNumber)
    {
        final String link = address + "/show";
        RestTemplate restTemplate = new RestTemplate();

        String encodedNumber = encodeNumber(phoneNumber);

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("id",encodedNumber);

        HttpEntity<List<Pigment>> response = restTemplate.exchange(
                componentsBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){});

        List<Pigment> pigments = response.getBody();
        return pigments;
    }


    public static List<Colors> getColors()
    {
        final String link = address + "/colors";
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<List<Colors>> response = restTemplate.exchange(
                link,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){});

        return response.getBody();
    }


    public static void showPigmentsList(Client client)
    {
        List<Pigment> list = getPigmentsList(client.getPhoneNumber());

        for (Pigment pigment: list) {
            pigment.pricePerGram(getColors());
        }

        System.out.println("\nPIGMENTS:\n___________________________________");
        for(Pigment pigment: list){
            System.out.println(pigment.toString());
        }
    }


    public static void choosePigments(Order order, int index, double weight){
        List<Pigment> list = getPigmentsList(order.getId());

        try{
            Pigment desiredPigment = list.get(index-1);
            desiredPigment.setWeight(weight);
            order.addPigment(desiredPigment);

            String pigmentName = desiredPigment.getName();
            System.out.println(pigmentName.toUpperCase()+"  ADDED TO ORDER.");
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

    }


    public static void createPigment(Order order, double[] array, double weight)
    {
        String clientId = order.getId();

        Pigment newPigment = new Pigment(clientId,array,getColors());
        newPigment.setWeight(weight);
        order.addPigment(newPigment);

        System.out.println("PIGMENT CREATED AND ADDED TO ORDER.");
    }

    private static List<Double> toList(Pigment pigment)
    {
        List<Double> list = new ArrayList<>();
        for (int i = 0;i<5;i++){
            double formulaPiece = pigment.getFormula(i);
            list.add(formulaPiece);
        }
        return list;
    }

    public static void savePigment(Order order, int index, String name)
    {
        final String link = address + "/save";
        RestTemplate restTemplate = new RestTemplate();
        index -= 1;

        Pigment pigment = order.getPigment(index);
        List<Double> formula = toList(pigment);

        String encodedNumber = encodeNumber(order.getId());

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("name", name)
                .queryParam("array",formula)
                .queryParam("clientId", encodedNumber);

        try{
            HttpEntity<String> response = restTemplate.exchange(
                    componentsBuilder.toUriString(),
                    HttpMethod.POST,
                    null,
                    String.class);
            System.out.println(response.getBody());
        }
        catch(HttpStatusCodeException exception){
            System.out.println("Code: "+exception.getRawStatusCode()+"\nResponse: "
                    + exception.getResponseBodyAsString());
        }

        order.getPigment(index).setName(name);
    }


    public static void deletePigment(String name, Client client)
    {
        final String link = address + "/";
        RestTemplate restTemplate = new RestTemplate();

        String encodedNumber = encodeNumber(client.getPhoneNumber());

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("pigmentName",name)
                .queryParam("clientNumber", encodedNumber);

        try{
            HttpEntity<String> response = restTemplate.exchange(
                    componentsBuilder.toUriString(),
                    HttpMethod.DELETE,
                    null,
                    String.class);
            System.out.println(response.getBody());
        }
        catch (HttpStatusCodeException exception){
            System.out.println("Code: "+exception.getRawStatusCode()+"\nResponse: "
                    + exception.getResponseBodyAsString());
        }

    }


    public static void alterPigment(Order order,int index, double[] array)
    {
        order.alterPigmentFromOrder(index,array,getColors());
    }


    public static void showEffects(){
        final String link = address + "/effects";
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<List<String>> response = restTemplate.exchange(
                link,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){});

        List<String> effects = response.getBody();

        for (int i = 0;i< effects.size();i++){
            System.out.println((i+1)+" "+effects.get(i));
        }
    }

    public static void addEffect(Order order, int index, int effect)
    {
        order.addEffectForPigment(index,effect);
    }

    public static void removeFromOrder(Order order, int index)
    {
        order.removePigment(index);
    }

    public static void showOrder(Order order)
    {
        order.showOrder();
    }

}
