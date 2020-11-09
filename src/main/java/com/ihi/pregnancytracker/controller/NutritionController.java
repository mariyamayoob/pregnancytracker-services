package com.ihi.pregnancytracker.controller;

import com.ihi.pregnancytracker.beans.NutritionInformation;
import com.ihi.pregnancytracker.service.NutritionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NutritionController {
  
  Logger logger = LoggerFactory.getLogger(NutritionController.class);
  
  @Autowired
  private NutritionService nutritionService;
  
  @GetMapping("/get/nutrition")
  public NutritionInformation getNutritionInformationByName
          (@RequestParam(value = "foodName") String foodName ) {
    return nutritionService.getNutritionInformation(foodName);
  }
  
}
