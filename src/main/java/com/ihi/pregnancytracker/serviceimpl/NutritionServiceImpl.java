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
    HttpResponse<String> response = Unirest.get("https://nutritionix-api.p.rapidapi.com/v1_1/search/"+ foodName+ "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat%2Cnf_calories_from_fat%2Cnf_total_fat%2Cnf_saturated_fat%2Cnf_monounsaturated_fat%2Cnf_polyunsaturated_fat%2Cnf_trans_fatty_acid%2Cnf_cholesterol%2Cnf_sodium%2Cnf_total_carbohydrate%2Cnf_dietary_fiber%2Cnf_sugars%2Cnf_protein%2Cnf_vitamin_a_dv%2Cnf_vitamin_c_dv%2Cnf_calcium_dv%2Cnf_iron_dv%2Cnf_potassium%2Cnf_servings_per_container%2Cnf_serving_size_qty%2Cnf_serving_size_unit%2Cnf_serving_weight_grams")
            .header("x-rapidapi-key", "520a217dd6msh65e35178a7359f9p13c100jsn8d1c10e53123")
            .header("x-rapidapi-host", "nutritionix-api.p.rapidapi.com")
            .asString();
    String resp_string = response.getBody();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    NutritionInformation nutritionInformation = null;
    try {
      nutritionInformation = objectMapper.readValue(resp_string, NutritionInformation.class);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
    }
    return nutritionInformation;
  }
}
