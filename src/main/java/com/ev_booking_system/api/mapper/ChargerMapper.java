package com.ev_booking_system.api.mapper;

import org.springframework.stereotype.Component;
import com.ev_booking_system.api.dto.ChargerDto;
import com.ev_booking_system.api.model.ChargerModel;

@Component
public class ChargerMapper {

    public ChargerDto toDto(ChargerModel model) {
        ChargerDto dto = new ChargerDto();
        dto.setId(model.getId());
        dto.setStationId(model.getStationId());
        dto.setConnectorType(model.getConnectorType());
        dto.setMaxPowerKw(model.getMaxPowerKw());
        dto.setStatus(model.getStatus().name().toLowerCase());
        dto.setOcppEndpointId(model.getOcppEndpointId());
        dto.setQrCode(model.getQrCode());
        dto.setName(model.getName());
        dto.setPortNumber(model.getPortNumber());
        return dto;
    }
}
