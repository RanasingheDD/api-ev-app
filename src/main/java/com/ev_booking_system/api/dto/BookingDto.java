package com.ev_booking_system.api.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    private String id;
    private String userId;
    private String chargerId;
    private String stationId;
    private Instant startAt;
    private Instant endAt;
    private String paymentId;
    private Double estimatedCost;
    private Double finalCost;
    private String evId;
    private Instant createdAt;
    private StationDto station;
    private ChargerDto charger;
    private String qrCode;
}
