package com.ev_booking_system.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargerDto {

    private String id;
    private String stationId;
    private String connectorType;
    private double maxPowerKw;
    private String status;          // available, occupied, charging, reserved, out-of-service
    private String ocppEndpointId;
    private String qrCode;
    private String name;
    private Integer portNumber;

}
