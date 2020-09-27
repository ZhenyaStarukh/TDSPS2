package com.example.pigments.services;

import com.example.pigments.objects.Pigment;
import com.example.pigments.objects.Colors;
import com.example.pigments.objects.Effect;
import com.example.pigments.repos.PigmentsRepo;
import com.example.pigments.repos.ClientsRepo;
import com.example.pigments.repos.ColorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;



@Service
public class PigmentService {

    private final ColorsRepo colorsRepo;
    private final PigmentsRepo pigmentsRepo;
    private final ClientsRepo clientsRepo;


    @Autowired
    public PigmentService(ClientsRepo clientsRepo, PigmentsRepo pigmentsRepo, ColorsRepo colorsRepo)
    {
        this.clientsRepo=clientsRepo;
        this.pigmentsRepo = pigmentsRepo;
        this.colorsRepo=colorsRepo;
    }



    public Pigment createAPigment(double[] array, String creatorId)
    {
        List<Colors> colors = colorsRepo.findAllByOrderByIdAsc();
        return new Pigment(creatorId,array,colors);
    }


    public void savePigment(Pigment pigment, String name) throws IllegalArgumentException
    {
        String clientPhone = pigment.getCreatorPhone();
        boolean canSavePigment = clientsRepo.existsByPhoneNumber(clientPhone);
        if(!canSavePigment)
            throw new IllegalArgumentException("Unregistered customers can't save their pigments");

        List<Colors> colors = colorsRepo.findAllByOrderByIdAsc();

        Pigment pigmentSaved = pigment.clone(colors);
        pigmentSaved.setName(name);
        pigmentsRepo.save(pigmentSaved);

    }


    private boolean inList(String name, String id)
    {
        List<Pigment> list = pigmentsRepo.findAllByCreatorPhone(id);

        for(Pigment pigment : list){
            if(pigment.getName().equals(name)) return true;
        }
        return false;
    }


    private Pigment desiredPigment(String name, String id)
    {
        List<Pigment> pigments = pigmentsRepo.findAllByCreatorPhone(id);

        for(Pigment pigment: pigments){
            if(pigment.getName().equals(name)) return pigment;
        }
        return null;
    }


    public void deletePigment(String name, String clientPhone) throws IllegalArgumentException
    {
        if (clientPhone.equals("None"))
            throw new IllegalArgumentException("Unregistered customers can't delete pigments from database");

        if (!inList(name, clientPhone))
            throw new IllegalArgumentException("There is no such custom pigment");

        Pigment desiredPigment = desiredPigment(name,clientPhone);
        pigmentsRepo.delete(Objects.requireNonNull(desiredPigment));
    }


    public List<Pigment> getAllPigments(String id)
    {
        List<Pigment> list = pigmentsRepo.findAllByCreatorPhone("Shop");
        Collections.reverse(list);

        List<Pigment> customList = pigmentsRepo.findAllByCreatorPhone(id);
        if(!id.equals("None")) list.addAll(customList);

        return list;
    }


    public List<Colors> getColors() {
        return colorsRepo.findAllByOrderByIdAsc();
    }


    public List<String> showEffects()
    {
        List<String> effects = new ArrayList<>();

        for(Effect effect: Effect.values()){
            effects.add(effect.toString());
        }
        return effects;
    }


    public void setWeight(double[] array)
    {
        List<Colors> colors = colorsRepo.findAllByOrderByIdAsc();

        for(int i = 0;i<colors.size();i++){
            colors.get(i).setWeight(array[i]);
            colorsRepo.save(colors.get(i));
        }

    }

    public double[] fromList(List<Double> list)
    {
        double[] array = new double[5];
        for(int i = 0;i< array.length;i++){
            array[i]= list.get(i);
        }
        return array;
    }

}
