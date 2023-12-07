package uz.pdp.eticket.service.locomotiveService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.LocomotiveCreateDto;
import uz.pdp.eticket.DTO.response.LocomotiveResponseDto;
import uz.pdp.eticket.entity.LocomotiveEntity;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.LocomotiveRepository;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<LocomotiveResponseDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LocomotiveEntity> locomotivePage = locomotiveRepository.findAllByIsActiveTrue(pageRequest);
        List<LocomotiveEntity> content = locomotivePage.getContent();
        return parse(content);
    }

    private LocomotiveEntity parse(LocomotiveCreateDto dto){
        return new LocomotiveEntity(dto.getName(), dto.getMaxSpeed(), dto.getMaxVagons());
    }


    @Override
    public LocomotiveResponseDto parse(LocomotiveEntity dto) {
        LocomotiveResponseDto map = modelMapper.map(dto, LocomotiveResponseDto.class);
        map.setCratedDate(dto.getCreatedDate());
        return map;
    }

    public List<LocomotiveResponseDto> parse(List<LocomotiveEntity> dto) {
        List<LocomotiveResponseDto>  list = new ArrayList<>();
        for (LocomotiveEntity locomotiveEntity : dto) {
            LocomotiveResponseDto map = modelMapper.map(locomotiveEntity, LocomotiveResponseDto.class);
            map.setCratedDate(locomotiveEntity.getCreatedDate());
            list.add(map);
        }
        return list;
    }

}

