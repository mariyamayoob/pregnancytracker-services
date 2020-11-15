package com.ihi.pregnancytracker.controller;

import com.ihi.pregnancytracker.beans.User;
import com.ihi.pregnancytracker.service.FhirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FHIRController {
  
  Logger logger = LoggerFactory.getLogger(FHIRController.class);
  
  @Autowired
  private FhirService fhirService;
  
  @GetMapping("/get/patient")
  public User getPatientDetailsById(@RequestParam(value = "id") String id) {
    return fhirService.getPatientDetailsById(id);
  }
  
}
