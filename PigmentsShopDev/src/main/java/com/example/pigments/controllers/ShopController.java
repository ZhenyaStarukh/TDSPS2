package com.example.pigments.controllers;

import com.example.pigments.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/pillow",produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService)
    {
        this.shopService = shopService;
    }



    @PutMapping
    public ResponseEntity<Object> setPillow(@RequestParam double pillow)
    {
        shopService.setPillow(pillow);
        return new ResponseEntity<>("PILLOW SET",
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getPillow()
    {
        return new ResponseEntity<>(shopService.getPillow(),
                HttpStatus.OK);
    }

}
