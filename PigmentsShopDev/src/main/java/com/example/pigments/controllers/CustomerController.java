package com.example.pigments.controllers;

import com.example.pigments.objects.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pigments.services.RegisterService;



@RestController
@RequestMapping(value="/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final RegisterService registerService;

    @Autowired
    public CustomerController(RegisterService registerService)
    {
        this.registerService = registerService;
    }


    @PostMapping("/register")
    public ResponseEntity<Object> createClient(@RequestParam String name,
                                               @RequestParam String phoneNumber)
    {
        String decodedPhoneNumber = phoneNumber.replace("%252B","+");

        Client client = new Client(name,decodedPhoneNumber);
        try{
            registerService.Register(client);
        }
        catch(Exception e){
            return new ResponseEntity<>("Bad request: "+e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(client.getName()+": successfully registered",
                HttpStatus.OK);

    }


    @GetMapping("/show")
    public  ResponseEntity<Object> getAllClients()
    {
          return new ResponseEntity<>(registerService.getAll(),
                  HttpStatus.OK);
    }

}
