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
    public PigmentController(PigmentService pigmentService){ this.pigmentService=pigmentService;}



    @GetMapping("/show")
    public ResponseEntity<List<Pigment>> showPigments(@RequestParam String id){
        return ResponseEntity.ok(pigmentService.getAllPigments(id.replace("%252B","+")));
    }


    @GetMapping("/effects")
    public ResponseEntity<Object> showEffects(){
        return new ResponseEntity<>(pigmentService.showEffects(), HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFromBase(@RequestParam String pigmentName,
                                                 @RequestParam String clientNumber){
        try{
            pigmentService.deletePigment(pigmentName, clientNumber.replace("%252B","+"));
        } catch (Exception e){
            return new ResponseEntity<>("Bad request: "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully deleted pigment '"+pigmentName+"'",HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<Object> savePigment(@RequestParam String name,
                                              @RequestParam double cyan,
                                              @RequestParam double magenta,
                                              @RequestParam double yellow,
                                              @RequestParam double black,
                                              @RequestParam double white,
                                              @RequestParam String clientId){
        try{
            pigmentService.savePigment(pigmentService.createAPigment(new double[]{cyan,magenta,yellow,black,white},
                    clientId.replace("%252B","+")),name);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("Bad request: "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>("Saved", HttpStatus.OK);
    }


    @GetMapping("/colors")
    public ResponseEntity<Object> getColors(){ return new ResponseEntity<>(pigmentService.getColors(),HttpStatus.OK);}


    @PutMapping("/colors")
    public ResponseEntity<Object> setWeight(@RequestParam double cyanWeight,
                                            @RequestParam double magentaWeight,
                                            @RequestParam double yellowWeight,
                                            @RequestParam double blackWeight,
                                            @RequestParam double whiteWeight){
        pigmentService.setWeight(new double[]{cyanWeight,magentaWeight,yellowWeight,blackWeight,whiteWeight});
        return new ResponseEntity<>("Weight of base pigments is altered", HttpStatus.OK);
    }

}
