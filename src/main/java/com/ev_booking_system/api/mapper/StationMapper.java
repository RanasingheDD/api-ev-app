package com.ev_booking_system.api.mapper;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ev_booking_system.api.dto.StationDto;
import com.ev_booking_system.api.model.StationModel;

@Component
public class StationMapper {

    @Autowired
    private ChargerMapper chargerMapper;

    @Autowired
    private TariffRuleMapper tariffRuleMapper;

    public StationDto toDto(StationModel m, Double distance) {

        StationDto dto = new StationDto();
        dto.setId(m.getId());
        dto.setName(m.getName());
        dto.setLat(m.getLat());
        dto.setLng(m.getLng());
        dto.setAddress(m.getAddress());
        dto.setOperatorId(m.getOperatorId());
        dto.setOperatorName(m.getOperatorName());
        dto.setImages(m.getImages());
        dto.setRating(m.getRating());
        dto.setReviewCount(m.getReviewCount());
        dto.setSupportsConnectors(m.getSupportsConnectors());
        dto.setDescription(m.getDescription());
        dto.setPhoneNumber(m.getPhoneNumber());
        dto.setAmenities(m.getAmenities());
        dto.setOperatingHours(m.getOperatingHours());
        dto.setOpen(m.isOpen());
        dto.setDistance(distance);

        if (m.getChargers() != null) {
            dto.setChargers(
                m.getChargers().stream()
                    .map(chargerMapper::toDto)
                    .collect(Collectors.toList())
            );
        }

        if (m.getTariffRules() != null) {
            dto.setTariffRules(
                m.getTariffRules().stream()
                    .map(tariffRuleMapper::toDto)
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public StationDto toDto(StationModel m) {
        return toDto(m, null);
    }
}
