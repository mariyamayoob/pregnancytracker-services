package com.ihi.pregnancytracker.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class AppUser {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long uID;
    @Column(unique = true)
    private String fhirID;
    private String name;
    private LocalDate pregnancyStartDate;
    private float prepregnacyWeight;
    private float c_d1;
    private float c_d2;
    private float c_d3;
    private float c_d4;
    private float c_d5;
    private float c_d6;
    private float c_d7;
    private float a_d1;
    private float a_d2;
    private float a_d3;
    private float a_d4;
    private float a_d5;
    private float a_d6;
    private float a_d7;
    private LocalDate lastUpdateDate;
    @Transient
    private boolean isUpdatedToday;

}
