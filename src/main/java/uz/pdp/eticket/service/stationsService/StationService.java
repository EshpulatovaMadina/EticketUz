package uz.pdp.eticket.service.stationsService;

import uz.pdp.eticket.DTO.request.StationsCreateDto;
import uz.pdp.eticket.DTO.response.StationResponseDto;

import java.util.List;
import java.util.UUID;

public interface StationService {
    StationResponseDto create(StationsCreateDto stationsCreateDto);

     StationResponseDto disActive(UUID stationId);

    StationResponseDto update(UUID seatId, StationsCreateDto dto);

    StationResponseDto isActive(UUID stationId);

    StationResponseDto getById(UUID stationId);

    List<StationResponseDto> getAll(int page, int size, String location);


}
