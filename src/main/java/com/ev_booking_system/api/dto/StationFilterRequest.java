package com.ev_booking_system.api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationFilterRequest {
    private Double lat;
    private Double lng;
    private Double radius;
    private List<String> connector;
    private Double minPower;
    private Double maxPrice;
    private Boolean available;
    private String operator;
    private String sortBy;

}
