package com.ev_booking_system.api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ev_booking_system.api.dto.ChargerDto;
import com.ev_booking_system.api.dto.StationDto;
import com.ev_booking_system.api.dto.StationFilterRequest;
import com.ev_booking_system.api.model.ChargerModel;
import com.ev_booking_system.api.model.StationModel;
import com.ev_booking_system.api.repository.StationRepository;
import com.ev_booking_system.api.service.StationService;

@RestController
@RequestMapping("/api/ev_stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private StationRepository stationRepository;

        @GetMapping        
        public ResponseEntity<Map<String, Object>> getStations(
                @RequestParam(required = false) Double lat,
                @RequestParam(required = false) Double lng,
                @RequestParam(required = false) Double radius,
                @RequestParam(required = false) List<String> connector,
                @RequestParam(required = false) Double minPower,
                @RequestParam(required = false) Double maxPrice,
                @RequestParam(required = false) Boolean available,
                @RequestParam(required = false) String operator,
                @RequestParam(required = false) String sortBy
        ) {

            StationFilterRequest filter = new StationFilterRequest();
            filter.setLat(lat);
            filter.setLng(lng);
            filter.setRadius(radius);
            filter.setConnector(connector);
            filter.setMinPower(minPower);
            filter.setMaxPrice(maxPrice);
            filter.setAvailable(available);
            filter.setOperator(operator);
            filter.setSortBy(sortBy);

            List<StationDto> stations = stationService.getStations(filter);

            return ResponseEntity.ok(Map.of("stations", stations));
        }
    
   
   @PostMapping("/add")
    public ResponseEntity<StationModel> addEv(@RequestBody StationModel stationModel) {
        StationModel savedEv = stationRepository.save(stationModel);
        return ResponseEntity.ok(savedEv);
    } 
/*     // Optional: List all EVs
    @GetMapping("/all")
    public ResponseEntity<?> getAllEvs() {
        return ResponseEntity.ok(stationRepository.findAll());
    }
*/
    @GetMapping("/{id}")
    public Optional<StationModel> getStationById(@PathVariable String id) {
        return stationRepository.findById(id);
    }

    @GetMapping("/{stationId}/chargers")
    public ResponseEntity<?> getStationChargers(@PathVariable String stationId) {
    List<ChargerDto> chargers = stationService.getStationChargers(stationId);

    return ResponseEntity.ok(Map.of("chargers", chargers));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchStations(@RequestParam("q") String query) {
        List<StationDto> stations = stationService.searchStations(query);
        return ResponseEntity.ok(Map.of("stations", stations));
    }



}
