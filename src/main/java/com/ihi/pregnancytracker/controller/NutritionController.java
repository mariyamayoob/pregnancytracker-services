package com.ihi.pregnancytracker.controller;

import com.ihi.pregnancytracker.beans.*;
import com.ihi.pregnancytracker.service.NutritionService;
import com.ihi.pregnancytracker.serviceimpl.NutritionServiceImpl;
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
  public List<Food> getNutritionInformationByName
          (@RequestParam(value = "foodName") String foodName ) {
    NutritionInformation nutritionInformation=nutritionService.getNutritionInformation(foodName);
    List<Food> foodList = new ArrayList<>();
    int size =  nutritionInformation.getFoods().size() > 10? 10: nutritionInformation.getFoods().size();
    for (int i = 0 ; i< size; i++){
      Food food= nutritionInformation.getFoods().get(i);
      List<String> nutStList = new ArrayList<>();
      for(int j =0; j<food.getFoodNutrients().size();j++) {
        FoodNutrients nut = food.getFoodNutrients().get(j);
        if(nut.getValue()>0){
          nutStList.add(nut.getNutrientName() + " :" + nut.getValue()+" "+nut.getUnitName());

        }

      }
      food.setFoodNutrients(null);
      food.setNutrientsStringList(nutStList);
        foodList.add(food);

    }

    return  foodList;
  }

  
}
