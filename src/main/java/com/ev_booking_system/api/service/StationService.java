package com.ev_booking_system.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ev_booking_system.api.dto.ChargerDto;
import com.ev_booking_system.api.dto.StationDto;
import com.ev_booking_system.api.dto.StationFilterRequest;
import com.ev_booking_system.api.mapper.ChargerMapper;
import com.ev_booking_system.api.mapper.StationMapper;
import com.ev_booking_system.api.model.ChargerModel;
import com.ev_booking_system.api.model.ChargerModel.ChargerStatus;
import com.ev_booking_system.api.model.StationModel;
import com.ev_booking_system.api.repository.StationRepository;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private ChargerMapper chargerMapper;

    public StationModel getStationById(String id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
    }


    public List<StationDto> getStations(StationFilterRequest filter) {

        List<StationModel> stations = stationRepository.findAll();
        List<StationDto> result = new ArrayList<>();

        for (StationModel s : stations) {

            Double distance = null;

            if (filter.getLat() != null && filter.getLng() != null) {
                distance = calculateDistance(
                        filter.getLat(), filter.getLng(),
                        s.getLat(), s.getLng()
                );

                if (filter.getRadius() != null && distance > filter.getRadius()) {
                    continue;
                }
            }

            // connector filter
            if (filter.getConnector() != null && !filter.getConnector().isEmpty()) {
                boolean matches = s.getSupportsConnectors()
                        .stream()
                        .anyMatch(filter.getConnector()::contains);

                if (!matches) continue;
            }

            // min power filter
            if (filter.getMinPower() != null) {
                boolean hasPower = s.getChargers()
                        .stream()
                        .anyMatch(c -> c.getMaxPowerKw() >= filter.getMinPower());

                if (!hasPower) continue;
            }

            // price filter
            if (filter.getMaxPrice() != null) {
                if (s.getTariffRules().isEmpty()) continue;
                double price = s.getTariffRules().get(0).getPrice();
                if (price > filter.getMaxPrice()) continue;
            }

            // availability
            if (Boolean.TRUE.equals(filter.getAvailable())) {
                boolean available = s.getChargers()
                        .stream()
                        .anyMatch(c -> c.getStatus() == ChargerStatus.AVAILABLE);
                if (!available) continue;
            }

            // operator filter
            if (filter.getOperator() != null) {
                if (!filter.getOperator().equals(s.getOperatorId())) continue;
            }

            // convert using mapper
            result.add(stationMapper.toDto(s, distance));
        }

        // sort
        if ("distance".equalsIgnoreCase(filter.getSortBy())) {
            result.sort(Comparator.comparing(StationDto::getDistance,
                    Comparator.nullsLast(Double::compareTo)));
        } else if ("rating".equalsIgnoreCase(filter.getSortBy())) {
            result.sort(Comparator.comparing(StationDto::getRating).reversed());
        }

        return result;
    }


    public List<ChargerDto> getStationChargers(String stationId) {
        StationModel station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        return station.getChargers().stream()
                .map(chargerMapper::toDto)
                .toList();
    }

    public ChargerModel getChargerById(String stationId, String chargerId) {
        // Get station
        StationModel station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        // Find matching charger
        return station.getChargers().stream()
                .filter(ch -> ch.getId().equals(chargerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Charger not found"));
    }




    public List<StationDto> searchStations(String query) {
        List<StationModel> results = stationRepository.searchByKeyword(query);
        return results.stream()
                .map(stationMapper::toDto)
                .toList();
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
