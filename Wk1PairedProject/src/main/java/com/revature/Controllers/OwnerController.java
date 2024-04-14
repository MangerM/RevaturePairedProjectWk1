package com.revature.Controllers;

import com.revature.DAOs.OwnerDAO;
import com.revature.Models.Owners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (u == null){
            return ResponseEntity.internalServerError().build();
        }else{
            return ResponseEntity.status(201).body(u);
        }
    }

}
