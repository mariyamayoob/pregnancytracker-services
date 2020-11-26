package com.ihi.pregnancytracker.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihi.pregnancytracker.beans.NutritionInformation;
import com.ihi.pregnancytracker.service.NutritionService;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.hl7.fhir.dstu3.model.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
@Service
public class NutritionServiceImpl implements NutritionService {

  Logger logger = LoggerFactory.getLogger(NutritionServiceImpl.class);

  @Override
  public NutritionInformation getNutritionInformation(String foodName) {
    HttpResponse<String> response = Unirest.get("https://api.nal.usda.gov/fdc/v1/foods/search?api_key=11bBgOgZGAYNOwXXYFJefxVF0g10ENLwMN3W5y01&query="+ foodName)

            .asString();
    String resp_string = response.getBody();
    System.out.println(resp_string);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    NutritionInformation nutritionInformation = null;
    try {
      nutritionInformation = objectMapper.readValue(resp_string, NutritionInformation.class);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
    }
    System.out.println(nutritionInformation);
    return nutritionInformation;
  }


}
