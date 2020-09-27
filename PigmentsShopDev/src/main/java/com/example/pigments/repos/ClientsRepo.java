package com.example.pigments.repos;

import org.springframework.data.repository.CrudRepository;
import com.example.pigments.objects.Client;
import java.util.List;



public interface ClientsRepo extends CrudRepository<Client, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    List<Client> findByName(String name);
    List<Client> findAllByOrderByIdDesc();
}
