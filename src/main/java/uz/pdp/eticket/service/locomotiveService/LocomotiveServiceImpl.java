package uz.pdp.eticket.service.locomotiveService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.LocomotiveCreateDto;
import uz.pdp.eticket.DTO.response.LocomotiveResponseDto;
import uz.pdp.eticket.entity.LocomotiveEntity;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.LocomotiveRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LocomotiveServiceImpl implements LocomotiveService {
    private final LocomotiveRepository locomotiveRepository;
    private final ModelMapper modelMapper;
    @Override
    public LocomotiveResponseDto create(LocomotiveCreateDto dto) {
        Optional<LocomotiveEntity> byName = locomotiveRepository.findByName(dto.getName());
        if (byName.isPresent()){
            throw new DataAlreadyExistsException("Locomotive name already exists");
        }
        LocomotiveEntity parse = parse(dto);
        locomotiveRepository.save(parse);
        return parse(parse);
    }

    @Override
    public String isInActive(UUID locomotiveId) {
        LocomotiveEntity locomotiveEntity = locomotiveRepository.findById(locomotiveId).orElseThrow(() -> new DataNotFoundException("Locomative not found !!!"));
        locomotiveEntity.setIsActive(false);
        locomotiveRepository.save(locomotiveEntity);
        return "Successfully";
    }

    @Override
    public LocomotiveResponseDto update(UUID locomotiveId, LocomotiveCreateDto dto) {
        LocomotiveEntity locomotiveEntity = locomotiveRepository.findById(locomotiveId).orElseThrow(() -> new DataNotFoundException("Locomative not found !!!"));
        modelMapper.map(dto, locomotiveEntity);
        return parse(locomotiveEntity);
    }

    @Override
    public LocomotiveResponseDto getById(UUID locomotiveId) {
        LocomotiveEntity locomotiveEntity = locomotiveRepository.findById(locomotiveId).orElseThrow(() -> new DataNotFoundException("Locomative not found !!!"));
        return parse(locomotiveEntity);
    }

    private LocomotiveEntity parse(LocomotiveCreateDto dto){
        return new LocomotiveEntity(dto.getName(), dto.getMaxSpeed(), dto.getMaxVagons());
    }


    @Override
    public LocomotiveResponseDto parse(LocomotiveEntity dto) {
        return modelMapper.map(dto, LocomotiveResponseDto.class);
    }

    @Override
    public LocomotiveEntity findById(UUID locomotiveId) {
        return locomotiveRepository.findById(locomotiveId).orElseThrow(() -> new DataNotFoundException("Locomative not found !!!"));
    }

    @Override
    public LocomotiveResponseDto isActive(UUID locoId) {
        LocomotiveEntity locomotive = locomotiveRepository.findById(locoId).orElseThrow(() -> new DataNotFoundException("Locomotive not found"));
            locomotive.setIsActive(true);
        return parse(locomotive);
    }
}

