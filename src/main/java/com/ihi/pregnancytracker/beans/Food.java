package com.ihi.pregnancytracker.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Food {

    private int fdcId;
    private String description;
    private String dataType;
    private String gtinUpc;
    private String brandOwner;
    private String ingredients;
    private List<FoodNutrients> foodNutrients;
    private List<String> nutrientsStringList;

}
