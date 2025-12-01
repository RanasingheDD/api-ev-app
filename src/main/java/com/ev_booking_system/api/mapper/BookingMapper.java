package com.ev_booking_system.api.mapper;

import org.springframework.stereotype.Component;

import com.ev_booking_system.api.dto.BookingDto;
import com.ev_booking_system.api.dto.ChargerDto;
import com.ev_booking_system.api.dto.StationDto;
import com.ev_booking_system.api.model.BookingModel;

@Component
public class BookingMapper {

    public static BookingDto toModel(
            BookingModel model,
            StationDto station,
            ChargerDto charger
    ) {
        BookingDto dto = new BookingDto();

        dto.setId(model.getId());
        dto.setUserId(model.getUserId());
        dto.setChargerId(model.getChargerId());
        dto.setStationId(model.getStationId());
        dto.setStartAt(model.getStartAt());
        dto.setEndAt(model.getEndAt());
        dto.setEstimatedCost(model.getEstimatedCost());
        dto.setFinalCost(model.getFinalCost());
        dto.setPaymentId(model.getPaymentId());
        dto.setQrCode(model.getQrCode());
        dto.setEvId(model.getEvId());
        dto.setCreatedAt(model.getCreatedAt());
        // Nested objects (optional)
        dto.setStation(station);
        dto.setCharger(charger);

        return dto;
    }
}
