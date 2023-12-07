package uz.pdp.eticket.service.stationsService;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.request.StationsCreateDto;
import uz.pdp.eticket.DTO.response.StationResponseDto;
import uz.pdp.eticket.DTO.response.StationRoadsResponseDto;
import uz.pdp.eticket.entity.RoadsEntity;
import uz.pdp.eticket.entity.StationEntity;
import uz.pdp.eticket.entity.StationRoadsEntity;
import uz.pdp.eticket.entity.VagonEntity;
import uz.pdp.eticket.exception.BadRequestException;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.RoadsRepository;
import uz.pdp.eticket.repository.StationRoadsRepository;
import uz.pdp.eticket.repository.StationsRepository;
import uz.pdp.eticket.service.roadsService.RoadsService;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {
    private final StationsRepository stationsRepository;
    private final ModelMapper modelMapper;
    private final StationRoadsService stationRoadsService;
    private final StationRoadsRepository sr;
    private final RoadsRepository roadsRepository;

    @Override
    public StationResponseDto create(StationsCreateDto station) {
        if(station.getRoadId() != null && station.getNumber() != null) {
            if (stationsRepository.existsAllByName(station.getName())){
                throw new DataAlreadyExistsException("This station name already exists !!!");
            }
            checkStation(station.getRoadId(), station.getNumber()); /// bu qator shunday raqamligi bor bolsa update qilib chiqadi.

            StationEntity map = modelMapper.map(station, StationEntity.class);  // buyerda qolganlarni kenga surib buni admin aytgan joyga qoyadi
            StationEntity save = stationsRepository.save(map);
            stationRoadsService.save(station.getRoadId(), List.of(new StationRoadCreateDto(save.getId(),station.getNumber())));
            return parse(save);
        }else if(station.getRoadId() == null && station.getNumber() == null) {
            StationEntity map = modelMapper.map(station, StationEntity.class);
            StationEntity save = stationsRepository.save(map);
            return parse(save);
        }else {
            throw new BadRequestException("Road id and order number both should be present or none");
        }
    }

    private boolean checkStation(UUID roadId, Integer number) {
        List<StationRoadsEntity> all = sr.findAllByRoadIdOrderByOrderNumber(roadId);
        for (int i = number; i < all.size(); i++) {
            all.get(i).setOrderNumber(i+1);
        }
        sr.saveAll(all);
        return true;
    }



    @Override
    public StationResponseDto disActive(UUID stationId) {
        StationEntity station = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        station.setIsActive(false);
        stationsRepository.save(station);
        return parse(station);
    }

    @Override
    public StationResponseDto update(UUID stationId, StationsCreateDto stationsCreateDto) {
        StationEntity station = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        modelMapper.map(stationsCreateDto, station);
        stationsRepository.save(station);
        return parse(station);
    }

    @Override
    public StationResponseDto isActive(UUID stationId) {
        StationEntity station = stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found."));
        station.setIsActive(true);
        stationsRepository.save(station);
        return parse(station);
    }

    @Override
    public StationResponseDto getById(UUID stationId) {
        return parse(stationsRepository.findById(stationId).orElseThrow(() -> new DataNotFoundException("Station not found")));
    }

    @Override
    public List<StationResponseDto> getAll(int page, int size, String location) {
        if (location.isEmpty()) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<StationEntity> stationPage = stationsRepository.findAllByIsActiveTrue(pageRequest);
            List<StationEntity> content = stationPage.getContent();
            return parse(content);
        }
        else return stationsRepository.findAllByLocationAndIsActiveTrue(location).stream().map(this::parse).toList();
    }

    
    private StationResponseDto parse(StationEntity stationEntity) {
        return modelMapper.map(stationEntity, StationResponseDto.class);
    }

    private List<StationResponseDto> parse(List<StationEntity> stations) {
        List<StationResponseDto> list = new ArrayList<>();
        for (StationEntity station : stations) {
           list.add( modelMapper.map(station, StationResponseDto.class));
        }
        return list;
    }



}
