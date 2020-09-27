package com.example.pigments.repos;

import org.springframework.data.repository.CrudRepository;
import com.example.pigments.objects.Pigment;
import java.util.List;



public interface PigmentsRepo extends CrudRepository<Pigment, Integer> {
    List<Pigment> findAllByCreatorPhone(String phoneNumber);
}
