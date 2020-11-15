package com.ihi.pregnancytracker.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString

public class User {
  
  private String firstName;
  private String lastName;
  private String gender;
  private Date dateOfBirth;
  private double age;
  private String height;    
  private String weight;
  private String glucose;
  private String ldl;
  private String hdl;
  private String totalCholesterol;
  private String sys;
  private String dia;
  private String calcium;
  private String bmi;
  private String hemoglobin;

  private List<FhirEntity> userConditionList;
  private List<FhirEntity> userMedicationList;

}