package com.ihi.pregnancytracker.beans;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Fields {

    private String item_name;
    private String brand_name;
    private double nf_calories;
    private double nf_total_fat;
    private int nf_serving_size_qty;
    private String nf_serving_size_unit;
}