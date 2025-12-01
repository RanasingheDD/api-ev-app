package com.ev_booking_system.api.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {
    private String id;
    private String name;
    private double lat;
    private double lng;
    private String address;
    private String operatorId;
    private String operatorName;
    private List<String> images;
    private double rating;
    private int reviewCount;
    private List<String> supportsConnectors;
    private List<TariffRuleDto> tariffRules;
    private List<ChargerDto> chargers;
    private String description;
    private String phoneNumber;
    private Map<String, String> operatingHours;
    private List<String> amenities;
    private boolean isOpen;
    private Double distance;

    // getters & setters
}
 
