package com.ihi.pregnancytracker.service;

import com.ihi.pregnancytracker.beans.User;

public interface FhirService {
  
  public User getPatientDetailsById(String name);

}
