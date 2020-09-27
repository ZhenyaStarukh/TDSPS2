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
    public RegisterService(ClientsRepo clientsRepo){ this.clientsRepo=clientsRepo;}


    public void Register(Client client) throws Exception
    {

        if (!client.getPhoneNumber().equals("None") && clientsRepo.existsByPhoneNumber(client.getPhoneNumber()))
            throw new Exception(client.getName()+"! You're already registered");

        else if (!client.getPhoneNumber().equals("None")) {
            clientsRepo.save(client);
        }

        else throw  new IllegalArgumentException("Should enter valid telephone number");
    }

    public List<Client> getAll(){
        List<Client> list = clientsRepo.findAllByOrderByIdDesc();

        list.remove(clientsRepo.findByName("Shop").get(0));
        return list;
    }

}
