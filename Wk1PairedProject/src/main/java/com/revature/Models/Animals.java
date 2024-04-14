package com.revature.Models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Animals")
@Component
public class Animals {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalID;


    @Column(nullable = false)
    private String animalName;


    @Column(nullable = false)
    private String animalType;


    @Column(nullable = false)
    private String animalStatus;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="ownerID")
    private Owners animalOwner;


    //Constructors


    public Animals() {
    }


    public Animals(int animalID, String animalName, String animalType, String animalStatus, Owners animalOwner) {
        this.animalID = animalID;
        this.animalName = animalName;
        this.animalType = animalType;
        this.animalStatus = animalStatus;
        this.animalOwner = animalOwner;
    }


    //Getter+Setter code


    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalStatus() {
        return animalStatus;
    }

    public void setAnimalStatus(String animalStatus) {
        this.animalStatus = animalStatus;
    }

    public Owners getAnimalOwner() {
        return animalOwner;
    }

    public void setAnimalOwner(Owners animalOwner) {
        this.animalOwner = animalOwner;
    }


    //To String
    @Override
    public String toString() {
        return "Animals{" +
                "animalID=" + animalID +
                ", animalName='" + animalName + '\'' +
                ", animalType='" + animalType + '\'' +
                ", animalStatus='" + animalStatus + '\'' +
                ", animalOwner=" + animalOwner +
                '}';
    }
}
