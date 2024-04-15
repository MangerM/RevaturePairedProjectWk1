package com.revature.DAOs;

import com.revature.Models.Animals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalDAO extends JpaRepository<Animals, Integer> {



}
