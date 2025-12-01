package com.ev_booking_system.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffRuleDto {
    private String id;
    private String type;          // per_kwh, per_minute, flat_fee, flat_plus_kwh
    private double price;
    private Double flatFee;
    private String currency;
    private String description;
    private String connectorType;
    private Double minPowerKw;
    private Double maxPowerKw;
    private TimeRangeDto peakHours;
    private Double peakMultiplier;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeRangeDto {
        private int startHour;
        private int endHour;
    }
}
