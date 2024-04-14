package com.revature.Controllers;

import com.revature.DAOs.AnimalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/animal")
@ResponseBody
public class AnimalController {

    AnimalDAO animalDAO;

    @Autowired
    public AnimalController(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }


}
