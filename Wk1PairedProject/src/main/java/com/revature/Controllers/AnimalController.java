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

    /*
    *
    * This is where we have code that "adopts" the animal
    *
    * */
    @PatchMapping("/adopt/{animalID}")
    public ResponseEntity<Object> adoptAnimalByID(@PathVariable int animalID, @RequestBody int ownerID){
        Optional<Animals> animal = animalDAO.findById(animalID);
        Optional<Owners> owner = ownerDAO.findById(ownerID);
        if(animal.isEmpty()){
            return ResponseEntity.status(404).body("No Animal found with the Id Provided "+ animalID);
        }
        if(owner.isEmpty()){
            return ResponseEntity.status(404).body("No Owner found with the Id Provided "+ ownerID);
        }
        Animals a = animal.get();
        Owners o = owner.get();
        if(a.getAnimalStatus().equals("Ready for Adoption")){
            a.setAnimalOwner(o);
            a.setAnimalStatus("Adopted");
            animalDAO.save(a);
            return ResponseEntity.ok("Animal " + a.getAnimalName() + " ID " + a.getAnimalID() + " has been adopted by " + o.getOwnerName());
        }else{
            return ResponseEntity.status(404).body("Animal " + a.getAnimalName() + " ID" + animalID + " Is not ready for adoption.");
        }
    }

    /*

    Here is where we can update an animal's contents

     */
    @PatchMapping("/{animalID}")
    public ResponseEntity<Object> updateAnimal(@PathVariable int animalID, @RequestBody Animals animal){
        Optional<Animals> animalExist = animalDAO.findById(animalID);
        if(animalExist.isEmpty()) {
            return ResponseEntity.status(404).body("No Animal found with the Id Provided " + animalID);
        }
        Animals a = animalExist.get();

        if(a.getAnimalOwner() == null && animal.getAnimalOwner() != null){
            return ResponseEntity.status(404).body("If you intend to adopt animal ID " + animalID + " Name " + a.getAnimalName() + " Please use /adopt/" + animalID);
        }

        a.setAnimalStatus(animal.getAnimalStatus());
        a.setAnimalOwner(animal.getAnimalOwner());
        a.setAnimalName(animal.getAnimalName());
        a.setAnimalType(animal.getAnimalType());
        animal.setAnimalID(a.getAnimalID());
        animalDAO.save(a);
        return ResponseEntity.status(201).body(animal);
    }
}
