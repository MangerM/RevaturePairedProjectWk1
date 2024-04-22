package com.revature.Controllers;

import com.revature.DAOs.AnimalDAO;
import com.revature.DAOs.OwnerDAO;
import com.revature.Models.Animals;
import com.revature.Models.Owners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value="/animals")
@ResponseBody
public class AnimalController {

    AnimalDAO animalDAO;
    OwnerDAO ownerDAO;


    @Autowired
    public AnimalController(AnimalDAO animalDAO, OwnerDAO ownerDAO) {
        this.animalDAO = animalDAO;
        this.ownerDAO = ownerDAO;
    }




    @PostMapping
    public ResponseEntity<Animals> insertAnimal(@RequestBody Animals animal){
        Animals a = animalDAO.save(animal);
        return ResponseEntity.status(201).body(a);
    }

    @PostMapping("/{ownerID}")
    public ResponseEntity<Object> insertAnimalWithOwner(@RequestBody Animals animal, @PathVariable int ownerID){

        Optional<Owners> o = ownerDAO.findById(ownerID);

        if(o.isEmpty()){
            return ResponseEntity.status(404).body("Chosen Owner doesn't exist.");
        }
        if(!animal.getAnimalStatus().equals("Adopted")){
            return ResponseEntity.status(404).body("Cannot create an animal with an owner without it being adopted");
        }

        Owners result = o.get();

        animal.setAnimalOwner(result);

        animalDAO.save(animal);
        return ResponseEntity.status(201).body(animal);
    }

    @GetMapping
    public ResponseEntity<List<Animals>> getAllAnimals(){

        List<Animals> animals = animalDAO.findAll();

        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{animalID}")
    public ResponseEntity<Object> getAnimalByID(@PathVariable int animalID){
        Optional<Animals> a = animalDAO.findById(animalID);

        if(a.isEmpty()){
            return ResponseEntity.status(404).body("No animal found with ID " + animalID);
        }else{
            return ResponseEntity.ok(a);
        }
    }

    @GetMapping("/ByOwnerID/{ownerID}")
    public ResponseEntity<List<Animals>> getAllAnimalsByOwner(@PathVariable int ownerID){

        
        
        List<Animals> animals = animalDAO.findAll();
        List<Animals> resultAnimals = new ArrayList<Animals>();
        for(Animals a : animals){
            if(a.getAnimalOwner() != null) {
                if (a.getAnimalOwner().getOwnerID() == ownerID) {
                    resultAnimals.add(a);
                }
            }
        }
        return ResponseEntity.ok(resultAnimals);
    }

    @DeleteMapping("/{animalID}")
    public ResponseEntity<Object> deleteAnimalByID(@PathVariable int animalID){
        Optional<Animals> animal = animalDAO.findById(animalID);
        if(animal.isEmpty()){
            return ResponseEntity.status(404).body("No Animal found with the Id Provided "+ animalID);
        }
        Animals a = animal.get();
        animalDAO.delete(a);
        return ResponseEntity.accepted().body("Animal with the ID " + a.getAnimalID() +" has been deleted");
    }

}
