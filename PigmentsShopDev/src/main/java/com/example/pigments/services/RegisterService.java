package com.example.pigments.services;

import com.example.pigments.objects.Client;
import  com.example.pigments.repos.ClientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class RegisterService {


    private final ClientsRepo clientsRepo;

    @Autowired
    public RegisterService(ClientsRepo clientsRepo)
    {
        this.clientsRepo=clientsRepo;
    }


    public void Register(Client client) throws Exception
    {
        String clientNumber = client.getPhoneNumber();
        String clientName = client.getName();

        boolean clientWithNumber = !clientNumber.equals("None");


        boolean isInDataBase = clientsRepo.existsByPhoneNumber(clientNumber);

        if ( clientWithNumber && isInDataBase)
            throw new Exception(clientName+"! You're already registered");

        else if (clientWithNumber) {
            clientsRepo.save(client);
        }

        else
            throw new IllegalArgumentException("Should enter valid telephone number");
    }

    public List<Client> getAll()
    {
        List<Client> list = clientsRepo.findAllByOrderByIdDesc();

        Client shop = clientsRepo.findByName("Shop").get(0);

        list.remove(shop);
        return list;
    }

}
