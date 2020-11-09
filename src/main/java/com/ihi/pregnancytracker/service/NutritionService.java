package com.ihi.pregnancytracker.service;


import com.ihi.pregnancytracker.beans.NutritionInformation;

public interface NutritionService {
  
  public NutritionInformation getNutritionInformation(String foodName);

}
