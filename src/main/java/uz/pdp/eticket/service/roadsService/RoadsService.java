package uz.pdp.eticket.service.roadsService;

import uz.pdp.eticket.DTO.request.RoadsCreateDto;
import uz.pdp.eticket.DTO.response.RoadsResponseDto;
import uz.pdp.eticket.entity.RoadsEntity;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface RoadsService {
    RoadsResponseDto getById(UUID roadsId);

    RoadsResponseDto isActive(UUID roadsId);

    RoadsResponseDto update(UUID roadsId, RoadsCreateDto dto);

    RoadsResponseDto deActive(UUID roadsId);

    RoadsResponseDto create(RoadsCreateDto roadsCreateDto);
    RoadsResponseDto getByDirection(String direction);

    RoadsResponseDto parse(RoadsEntity roadsEntity);
}
