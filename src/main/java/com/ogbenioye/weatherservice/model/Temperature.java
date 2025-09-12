package com.ogbenioye.weatherservice.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Temperature {
    private Double day;
    private Double min;
    private Double max;
    private Double night;
    private Double eve;
    private Double morn;
}
