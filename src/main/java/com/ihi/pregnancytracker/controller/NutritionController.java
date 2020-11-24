package com.ihi.pregnancytracker.controller;

import com.ihi.pregnancytracker.beans.Fields;
import com.ihi.pregnancytracker.beans.Hits;
import com.ihi.pregnancytracker.beans.NutritionInformation;
import com.ihi.pregnancytracker.service.NutritionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
public class NutritionController {
  
  Logger logger = LoggerFactory.getLogger(NutritionController.class);
  
  @Autowired
  private NutritionService nutritionService;
  
  @GetMapping("/get/nutrition")
  public List<Fields> getNutritionInformationByName
          (@RequestParam(value = "foodName") String foodName ) {
    NutritionInformation nutritionInformation=nutritionService.getNutritionInformation(foodName);
    List<Fields> fieldsList = new ArrayList<>();
    for(Hits hits:nutritionInformation.getHits() ){
      fieldsList.add(hits.getFields());

    };
    return  fieldsList;
  }
  
}
