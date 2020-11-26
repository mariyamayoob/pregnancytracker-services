package com.ihi.pregnancytracker.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class NutritionInformation {
    private List<Food> foods;

}