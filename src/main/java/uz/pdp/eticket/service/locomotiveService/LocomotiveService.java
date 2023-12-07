package uz.pdp.eticket.service.locomotiveService;

import org.springframework.data.domain.Page;
import uz.pdp.eticket.DTO.request.LocomotiveCreateDto;
import uz.pdp.eticket.DTO.response.LocomotiveResponseDto;
import uz.pdp.eticket.entity.LocomotiveEntity;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface LocomotiveService {
     LocomotiveResponseDto create(LocomotiveCreateDto dto);
     String isInActive(UUID locomotiveId);
     LocomotiveResponseDto update(UUID locomotiveId, LocomotiveCreateDto dto);
     LocomotiveResponseDto getById(UUID locomotiveId);
     LocomotiveResponseDto parse(LocomotiveEntity dto);
     LocomotiveEntity findById(UUID locomotiveId);

     LocomotiveResponseDto isActive(UUID locoId);

    List<LocomotiveResponseDto> getAll(int page, int size);

}
