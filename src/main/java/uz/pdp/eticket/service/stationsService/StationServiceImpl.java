package uz.pdp.eticket.service.stationsService;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.request.StationsCreateDto;
import uz.pdp.eticket.DTO.response.StationResponseDto;
import uz.pdp.eticket.entity.StationEntity;
import uz.pdp.eticket.exception.BadRequestException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.StationsRepository;
import uz.pdp.eticket.service.roadsService.RoadsService;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {
    private final StationsRepository stationsRepository;
    private final ModelMapper modelMapper;
    private final StationRoadsService stationRoadsService;

    @Override
    public StationResponseDto create(StationsCreateDto stationsCreateDto) {
        if(stationsCreateDto.getRoadId() != null && stationsCreateDto.getNumber() != null) {
            StationEntity map = modelMapper.map(stationsCreateDto, StationEntity.class);
            StationEntity save = stationsRepository.save(map);
            stationRoadsService.save(stationsCreateDto.getRoadId(), List.of(new StationRoadCreateDto(save.getId(),stationsCreateDto.getNumber())));
            return parse(save);
        }else if(stationsCreateDto.getRoadId() == null && stationsCreateDto.getNumber() == null) {
            StationEntity map = modelMapper.map(stationsCreateDto, StationEntity.class);
            StationEntity save = stationsRepository.save(map);
            return parse(save);
        }else {
            throw new BadRequestException("Road id and order number both should be present or none");
        }
    }

    @Override
    public StationResponseDto deActive(UUID stationId) {
        StationEntity station = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        station.setIsActive(false);
        stationsRepository.save(station);
        return parse(station);
    }

    @Override
    public StationResponseDto update(UUID stationId, StationsCreateDto dto) {
        StationEntity stationEntity = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        modelMapper.map(dto, stationEntity);
        return parse(stationEntity);
    }

    @Override
    public StationResponseDto isActive(UUID stationId) {
        StationEntity stationEntity = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        stationEntity.setIsActive(true);
        stationsRepository.save(stationEntity);
        return parse(stationEntity);
    }

    @Override
    public StationResponseDto getById(UUID stationId) {
        StationEntity stationEntity = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        return parse(stationEntity);
    }

    @Override
    public List<StationResponseDto> getAll(String location) {
        if (location.isEmpty())
            return stationsRepository.findAll().stream().map(this::parse).toList();
        else return stationsRepository.findAllByLocation(location).stream().map(this::parse).toList();
    }

    
    private StationResponseDto parse(StationEntity stationEntity) {
        return modelMapper.map(stationEntity, StationResponseDto.class);
    }

}
