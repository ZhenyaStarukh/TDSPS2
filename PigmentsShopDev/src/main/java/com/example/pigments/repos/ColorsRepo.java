package com.example.pigments.repos;

import com.example.pigments.objects.Colors;
import org.springframework.data.repository.CrudRepository;
import java.util.List;



public interface ColorsRepo extends CrudRepository<Colors, Integer> {
    List<Colors> findAllByOrderByIdAsc();
}
