package com.ihi.pregnancytracker.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class AppUser {
    @Id
    private int uID;
    private String fhirID;
    private String name;
    private Date pregnancyStartDate;
    private float prepregnacyWeight;
    private float prepregnacyBMI;
    private float c_d1;
    private float c_d2;
    private float c_d3;
    private float c_d4;
    private float c_d5;
    private float c_d6;
    private float c_d7;

}
