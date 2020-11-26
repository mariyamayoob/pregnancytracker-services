package com.ihi.pregnancytracker.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FoodNutrients {

            private String nutrientName;
            private String unitName; 
            private int value;
    }