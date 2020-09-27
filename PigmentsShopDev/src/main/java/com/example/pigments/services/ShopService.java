package com.example.pigments.services;

import com.example.pigments.objects.*;
import com.example.pigments.repos.ShopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ShopService {

    private final ShopRepo shopRepo;
    private final Shop shop;


    @Autowired
    public ShopService(ShopRepo shopRepo)
    {
        this.shopRepo=shopRepo;
        shop = shopRepo.findAll().get(0);
    }

    public void setPillow(double pillow){
        shop.setPillow(pillow);
        shopRepo.save(shop);
    }

    public double getPillow(){
        return shop.getPillow();
    }

}
