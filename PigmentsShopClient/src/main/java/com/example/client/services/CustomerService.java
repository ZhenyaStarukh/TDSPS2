package com.example.client.services;

import com.example.client.objects.Client;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;



@Service
public class CustomerService {

    private static final String address = "http://localhost:8080/clients";


    public static Client createCustomer(String name, String number){
        return new Client(name,number);
    }


    public static void registerCustomer(Client client, String ans){

        if (ans.equals("no")){
            client.resetId();
            System.out.println(client.getName()+"decided not to register.");
            return;
        }

        final String link = address + "/register";
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("name", client.getName())
                .queryParam("phoneNumber",client.getPhoneNumber().replace("+","%2B"));

        try{
            HttpEntity<String> response = restTemplate.exchange(componentsBuilder.toUriString(),HttpMethod.POST,
                    null,String.class);
            System.out.println(response.getBody());
        }catch(HttpStatusCodeException exception){
            System.out.println("Code: "+exception.getRawStatusCode()+"\nResponse: "
                    + exception.getResponseBodyAsString());
        }

    }


    public static void showClients(){
        final String link = address + "/show";
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(link);

        HttpEntity<List<Client>> response = restTemplate.exchange(componentsBuilder.toUriString(), HttpMethod.GET,
                null, new ParameterizedTypeReference<>(){});

        List<Client> clients = response.getBody();
        System.out.println("\nCLIENTS:\n___________________________________");

        assert clients != null;
        for (Client client : clients){
            System.out.println(client.toString());
        }
    }
}
