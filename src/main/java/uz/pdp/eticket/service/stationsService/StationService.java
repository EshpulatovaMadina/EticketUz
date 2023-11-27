package uz.pdp.eticket.service.stationsService;

import uz.pdp.eticket.DTO.request.StationsCreateDto;
import uz.pdp.eticket.DTO.response.StationsResponseDto;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface StationService {
    StationsResponseDto create(StationsCreateDto stationsCreateDto);

     StationsResponseDto deActive(UUID stationId);

    StationsResponseDto update(UUID seatId, StationsCreateDto dto);

    StationsResponseDto isActive(UUID stationId);

    StationsResponseDto getById(UUID seatId);

}
