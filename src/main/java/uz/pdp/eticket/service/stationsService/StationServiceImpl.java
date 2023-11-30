package uz.pdp.eticket.service.stationsService;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.request.StationsCreateDto;
import uz.pdp.eticket.DTO.response.StationsResponseDto;
import uz.pdp.eticket.entity.StationsEntity;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.StationsRepository;
import uz.pdp.eticket.service.roadsService.RoadsService;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService{
    private final RoadsService roadsService;
    private final StationsRepository stationsRepository;
    private final ModelMapper modelMapper;
    private final StationRoadsService stationRoadsService;
    @Override
    public StationsResponseDto create(StationsCreateDto stationsCreateDto) {
        StationsEntity map = modelMapper.map(stationsCreateDto, StationsEntity.class);
        StationsEntity save = stationsRepository.save(map);

        if (stationsCreateDto.getRoadId() != null) {
            stationRoadsService.save(stationsCreateDto.getRoadId(), List.of(new StationRoadCreateDto(save.getId(), save.getSequenceNumber())));
        }
        return parse(save);
    }

    @Override
    public StationsResponseDto deActive(UUID stationId) {
        StationsEntity stationsEntity = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        stationsEntity.setIsActive(false);
        stationsRepository.save(stationsEntity);
        return parse(stationsEntity);
    }

    @Override
    public StationsResponseDto update(UUID stationId, StationsCreateDto dto) {
        StationsEntity stationsEntity = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
         modelMapper.map(dto, stationsEntity);
        return parse(stationsEntity);
    }

    @Override
    public StationsResponseDto isActive(UUID stationId) {
        StationsEntity stationsEntity = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        stationsEntity.setIsActive(true);
        stationsRepository.save(stationsEntity);
        return parse(stationsEntity);
    }

    @Override
    public StationsResponseDto getById(UUID stationId) {
        StationsEntity stationsEntity = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        return parse(stationsEntity);
    }



    private StationsResponseDto parse(StationsEntity stationsEntity){
        //        RoadsResponseDto responseDto = roadsService.parse(stationsEntity.getRoadsEntity());
//        map.setRoadsResponseDto();
        return modelMapper.map(stationsEntity, StationsResponseDto.class);
    }
}
