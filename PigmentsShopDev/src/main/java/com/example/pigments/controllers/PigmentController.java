package com.example.pigments.controllers;

import com.example.pigments.objects.Pigment;
import com.example.pigments.services.PigmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping(value="/pigments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PigmentController {

    private final PigmentService pigmentService;


    @Autowired
    public PigmentController(PigmentService pigmentService)
    {
        this.pigmentService=pigmentService;
    }


    private String decodedNumber(String number)
    {
        return number.replace("%252B","+");
    }


    @GetMapping("/show")
    public ResponseEntity<List<Pigment>> showPigments(@RequestParam String id)
    {
        String decodedId = decodedNumber(id);
        List<Pigment> pigments = pigmentService.getAllPigments(decodedId);

        return ResponseEntity.ok(pigments);
    }


    @GetMapping("/effects")
    public ResponseEntity<Object> showEffects()
    {
        return new ResponseEntity<>(pigmentService.showEffects(),
                HttpStatus.OK);
    }


    @DeleteMapping("/")
    public ResponseEntity<String> deleteFromBase(@RequestParam String pigmentName,
                                                 @RequestParam String clientNumber)
    {
        String decodedNumber = decodedNumber(clientNumber);
        try{
            pigmentService.deletePigment(pigmentName, decodedNumber);
        }
        catch (Exception e){
            return new ResponseEntity<>("Bad request: "+e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully deleted pigment '"+pigmentName+"'",
                HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<Object> savePigment(@RequestParam String name,
                                              @RequestParam List<Double> array,
                                              @RequestParam String clientId)
    {
        String decodedId = decodedNumber(clientId);

        Pigment pigment = pigmentService.createAPigment(
                pigmentService.fromList(array),
                decodedId);

        try{
            pigmentService.savePigment(pigment, name);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>("Bad request: "
                    + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>("Saved",
                HttpStatus.OK);
    }


    @GetMapping("/colors")
    public ResponseEntity<Object> getColors()
    {
        return new ResponseEntity<>(pigmentService.getColors(),
                HttpStatus.OK);
    }


    @PutMapping("/colors")
    public ResponseEntity<Object> setWeight(@RequestParam List<Double> weight){

        double[] weightArray = pigmentService.fromList(weight);

        pigmentService.setWeight(weightArray);
        return new ResponseEntity<>("Weight of base pigments is altered",
                HttpStatus.OK);
    }

}
