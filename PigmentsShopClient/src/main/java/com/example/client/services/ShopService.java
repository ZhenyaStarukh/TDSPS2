package com.example.client.services;

import com.example.client.objects.Cashier;
import com.example.client.objects.Colors;
import com.example.client.objects.Order;
import com.example.client.objects.Shop;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;



@Service
public class ShopService {

    private static Shop shop;
    private static Cashier cashier;
    private static List<Colors> colors;

    public static void makePurchase(Order order, String ans)
    {
        try{
            Purchase(order,ans,cashier);
        } catch(Exception exception){
            System.out.println("Error: "+exception.getMessage());
        }

    }


    public static void openShop()
    {
        shop = new Shop();
        cashier = new Cashier();

        final String link = "http://localhost:8080/pillow";
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Double> response = restTemplate.exchange(
                link,
                HttpMethod.GET,
                null,
                Double.class);

        double receivedPillow = response.getBody();

        shop.setPillow(receivedPillow);

        colors = OrderService.getColors();

    }


    private static void checkPigments(Order order) throws Exception
    {

        for (int i = 0; i < colors.size(); i++) {
            if (order.countPigment(i) > colors.get(i).getWeight())
            {
                System.out.println("Not enough " + colors.get(i).getName() + " pigment.");
                throw new Exception("Not enough pigment.");
            }
        }

    }


    private static boolean haveDiscount(Order order)
    {
        return !order.getId().equals("None");
    }


    private static void Purchase(Order order, String ans, Cashier cashier) throws Exception
    {


        checkPigments(order);
        order.calculateTotalPrice(colors);
        order.showTotal(colors);


        if (ans.equals("no"))
        {
            System.out.println("_____Order cancelled_____");
            return;
        }

        if (haveDiscount(order)) order.setDiscount();


        double totalPrice = order.getTotalPrice();

        order.showTotal(colors);

        cashier.addToCashier(totalPrice);

        for (int i = 0; i < colors.size(); i++)
        {
            double weightToReduce = order.countPigment(i);
            colors.get(i).reduceWeight(weightToReduce);

            System.out.println(colors.get(i).getWeight());
        }
        System.out.println("_______________\n");
    }

    private static List<Double> toList()
    {
        List<Double> list = new ArrayList<>();
        for(int i = 0;i<5;i++) {
            list.add(colors.get(i).getWeight());
        }
           return list;

    }

    public static void closeShop()
    {
        double income = cashier.getCurrentBalance();
        double expenses = ExpensesService.buyPigments(colors);

        ExpensesService.addToPillow(income,expenses,shop);
        cashier.resetCurrentBalance();

        RestTemplate restTemplate = new RestTemplate();

        String link = "http://localhost:8080/pillow";
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("pillow",shop.getPillow());

        HttpEntity<String> response = restTemplate.exchange(componentsBuilder.toUriString(),
                HttpMethod.PUT,
                null,
                String.class);

        System.out.println(response.getBody());


        link = "http://localhost:8080/pigments/colors";
        componentsBuilder = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("weight",toList());

        response = restTemplate.exchange(componentsBuilder.toUriString(),
                HttpMethod.PUT,
                null,
                String.class);

        System.out.println(response.getBody());

    }

}
