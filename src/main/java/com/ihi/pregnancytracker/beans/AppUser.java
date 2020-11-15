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

}
