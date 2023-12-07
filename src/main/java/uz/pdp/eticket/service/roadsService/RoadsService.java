package uz.pdp.eticket.service.roadsService;

import uz.pdp.eticket.DTO.request.RoadsCreateDto;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.response.RoadsResponseDto;
import uz.pdp.eticket.entity.RoadsEntity;

import java.util.List;
import java.util.UUID;

public interface RoadsService {
    RoadsResponseDto getById(UUID roadsId);
    RoadsEntity findById(UUID roadsId);

    RoadsResponseDto isActive(UUID roadsId);

    RoadsResponseDto update(UUID roadsId, RoadsCreateDto dto);

    RoadsResponseDto disActive(UUID roadsId);

    RoadsResponseDto create(RoadsCreateDto roadsCreateDto);
    RoadsResponseDto getByDirection(String direction);

    RoadsResponseDto parse(RoadsEntity roadsEntity);

    RoadsResponseDto setStation(UUID roadId, List<StationRoadCreateDto> stations);

    List<RoadsResponseDto> getAll(int page, int size);

}
