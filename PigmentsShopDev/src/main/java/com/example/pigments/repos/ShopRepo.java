package com.example.pigments.repos;

import com.example.pigments.objects.Shop;
import org.springframework.data.repository.CrudRepository;
import java.util.List;



public interface ShopRepo extends CrudRepository<Shop, Integer> {
    @Override
    List<Shop> findAll();
}
