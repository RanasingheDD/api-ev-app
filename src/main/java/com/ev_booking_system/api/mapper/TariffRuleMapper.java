package com.ev_booking_system.api.mapper;

import org.springframework.stereotype.Component;
import com.ev_booking_system.api.dto.TariffRuleDto;
import com.ev_booking_system.api.model.TariffRuleModel;

@Component
public class TariffRuleMapper {

    public TariffRuleDto toDto(TariffRuleModel tr) {
        TariffRuleDto dto = new TariffRuleDto();

        dto.setId(tr.getId());
        dto.setType(tr.getType().name().toLowerCase());
        dto.setPrice(tr.getPrice());
        dto.setFlatFee(tr.getFlatFee());
        dto.setCurrency(tr.getCurrency());
        dto.setDescription(tr.getDescription());
        dto.setConnectorType(tr.getConnectorType());
        dto.setMinPowerKw(tr.getMinPowerKw());
        dto.setMaxPowerKw(tr.getMaxPowerKw());

        if (tr.getPeakHours() != null) {
            TariffRuleDto.TimeRangeDto time = new TariffRuleDto.TimeRangeDto();
            time.setStartHour(tr.getPeakHours().getStartHour());
            time.setEndHour(tr.getPeakHours().getEndHour());
            dto.setPeakHours(time);
        }

        dto.setPeakMultiplier(tr.getPeakMultiplier());
        return dto;
    }
}
