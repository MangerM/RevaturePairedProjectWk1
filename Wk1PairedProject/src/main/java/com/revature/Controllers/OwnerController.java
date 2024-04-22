package com.revature.Controllers;

import com.revature.DAOs.OwnerDAO;
import com.revature.Models.Owners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/owners")
@ResponseBody
public class OwnerController {

    OwnerDAO ownerDAO;

    @Autowired
    public OwnerController(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }


    @PostMapping
    public ResponseEntity<Owners> insertUser(@RequestBody Owners Owner){
        Owners u = ownerDAO.save(Owner);

        return ResponseEntity.status(201).body(u);

    }

    @GetMapping
    public ResponseEntity<List<Owners>> getAllOwners(){

        List<Owners> owners = ownerDAO.findAll();

        return ResponseEntity.ok(owners);
    }

    @GetMapping("/{ownerID}")
    public ResponseEntity<Object> getOwnerByID(@PathVariable int ownerID){
        Optional<Owners> o = ownerDAO.findById(ownerID);

        if(o.isEmpty()){
            return ResponseEntity.status(404).body("No owner found with ID " + ownerID);
        }else{
            return ResponseEntity.ok(o);
        }
    }


    /*
    *
    * This is the update owner(user) code
    *
    * */
    @PatchMapping("/{ownerId}")
    public ResponseEntity<Object> patchOwner(@RequestBody int age, @PathVariable int ownerId){

        Optional<Owners> o = ownerDAO.findById(ownerId);

        if(o.isEmpty()){
            return ResponseEntity.status(404).body("No Owner found with ID " + ownerId);
        }else{
            Owners owner = o.get();
            owner.setOwnerAge(age);
            ownerDAO.save(owner);
            return ResponseEntity.accepted().body(owner);
        }

    }

}
