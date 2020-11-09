package com.ihi.pregnancytracker.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihi.pregnancytracker.beans.NutritionInformation;
import com.ihi.pregnancytracker.service.NutritionService;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NutritionServiceImpl implements NutritionService {

  Logger logger = LoggerFactory.getLogger(NutritionServiceImpl.class);

  @Override
  public NutritionInformation getNutritionInformation(String foodName) {
    HttpResponse<String> response = Unirest.get("https://nutritionix-api.p.rapidapi.com/v1_1/search/"+ foodName+ "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat")
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
