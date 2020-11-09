package com.ihi.pregnancytracker.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString

public class User {
  
  private String firstName;
  private String lastName;
  private String gender;
  private Date birthdate;
  private String height;    
  private String weight;
  private String bloodPressure;
  private String glucose;
  private String cholesterol;
  private String triglycerides;
  private String calcium;
  private String bmi;
  
}