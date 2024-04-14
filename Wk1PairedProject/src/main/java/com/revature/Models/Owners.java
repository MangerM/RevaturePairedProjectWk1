package com.revature.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "Owners")
@Component
public class Owners {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ownerID;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private int ownerAge;


    //Constructors


    public Owners() {
    }

    public Owners(int ownerID, String ownerName, int ownerAge) {
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.ownerAge = ownerAge;
    }



    //Getter+Setter code

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getOwnerAge() {
        return ownerAge;
    }

    public void setOwnerAge(int ownerAge) {
        this.ownerAge = ownerAge;
    }







}
