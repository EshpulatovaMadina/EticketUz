package uz.pdp.eticket.service.roadsService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.eticket.DTO.request.RoadsCreateDto;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.response.RoadsResponseDto;
import uz.pdp.eticket.DTO.response.StationResponseDto;
import uz.pdp.eticket.DTO.response.StationRoadsResponseDto;
import uz.pdp.eticket.entity.RoadsEntity;
import uz.pdp.eticket.entity.StationEntity;
import uz.pdp.eticket.entity.StationRoadsEntity;
import uz.pdp.eticket.entity.VagonEntity;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.RoadsRepository;
import uz.pdp.eticket.repository.StationRoadsRepository;
import uz.pdp.eticket.repository.StationsRepository;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RoadsServiceImpl implements RoadsService {
    private final RoadsRepository roadsRepository;
    private final ModelMapper modelMapper;
    private final StationRoadsService stationRoadsService;
    private final StationRoadsRepository sr;
    private final StationsRepository stationsRepository;

    @Transactional
    @Override
    public RoadsResponseDto create(RoadsCreateDto roadsCreateDto) {
        if (roadsRepository.existsByDirection(roadsCreateDto.getDirection())) {
            throw new DataAlreadyExistsException("This Road name already exists . Please can you create other name?");
        }
        RoadsEntity road = new RoadsEntity(roadsCreateDto.getDirection());
        roadsRepository.save(road);
        stationRoadsService.save(road.getId(),roadsCreateDto.getStations());
        List<StationRoadsResponseDto> stationOfRoad = stationRoadsService.getStationOfRoad(road.getId());
        List<StationResponseDto> dtoList = parseToStation(stationOfRoad);
        return new RoadsResponseDto(road.getId(), road.getDirection(), dtoList);
    }
    private List<StationResponseDto> parseToStation(List<StationRoadsResponseDto> stationOfRoad){
        List<StationResponseDto> list = new ArrayList<>();
        for (StationRoadsResponseDto s : stationOfRoad) {
            list.add(new StationResponseDto(s.getId(), s.getRoadName(), s.getCreatedDate()));
        }
        return list;
    }

    @Override
    public RoadsResponseDto update(UUID roadsId, RoadsCreateDto dto) {
        RoadsEntity road = roadsRepository.findById(roadsId).orElseThrow(() -> new DataNotFoundException("Roads not found"));
        road.setDirection(dto.getDirection());
        stationRoadsService.update(road.getId(), dto.getStations());
        return new RoadsResponseDto(roadsId, road.getDirection());
    }

    @Override
    public RoadsResponseDto getByDirection(String direction) {
        RoadsEntity road = roadsRepository.findAllByDirection(direction).orElseThrow(() -> new DataNotFoundException("This road not found"));
        List<StationRoadsEntity> all = sr.findAllByRoadIdOrderByOrderNumber(road.getId());
        List<StationResponseDto> parse = parse(all);
        return new RoadsResponseDto(road.getId(), road.getDirection(), parse);
        /**
         * shuyerda responseda stansiyalari bormadi
         */
    }



    @Override
    public RoadsResponseDto setStation(UUID roadId, List<StationRoadCreateDto> stations) {
        RoadsEntity road = roadsRepository.findById(roadId).orElseThrow(() -> new DataNotFoundException("Road not found !!!"));

        List<StationRoadsEntity> all = sr.findAllByRoadId(roadId);

        List<UUID> newIdList = stations.stream().map(StationRoadCreateDto::getStationId).collect(toList());

        List<UUID> oldIdList = all.stream().map(item->item.getStation().getId()).toList();

        List<StationRoadsEntity> saveList = stations.stream().filter(item->!oldIdList.contains(item.getStationId())).map(item->{
            StationEntity entity1 = stationsRepository.findById(item.getStationId()).orElseThrow(() -> new DataNotFoundException(" station not found"));
            return new StationRoadsEntity(entity1,road, item.getOrderNumber());
        }).toList();

        List<StationRoadsEntity> deleteAll = all.stream().filter(item->newIdList.contains(item.getStation().getId())).toList();

        sr.deleteAll(deleteAll);
        sr.saveAll(saveList);
        return null;
    }

    @Override
    public List<RoadsResponseDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RoadsEntity> roadPage = roadsRepository.findAllByIsActiveTrue(pageRequest);
        List<RoadsEntity> content = roadPage.getContent();
        return parse1(content);
    }



    @Override
    public RoadsResponseDto getById(UUID roadsId) {
        RoadsEntity road = roadsRepository.findById(roadsId).orElseThrow(() -> new DataNotFoundException("Roads not found"));
        return modelMapper.map(road, RoadsResponseDto.class);
    }

    @Override
    public RoadsEntity findById(UUID roadsId) {
        return roadsRepository.findById(roadsId)
                .orElseThrow(()-> new DataNotFoundException("Road not found with id: "+roadsId));
    }

    @Override
    public RoadsResponseDto isActive(UUID roadsId) {
        RoadsEntity road = roadsRepository.findById(roadsId).orElseThrow(() -> new DataNotFoundException("Roads not found"));
        road.setIsActive(true);
        roadsRepository.save(road);
        return modelMapper.map(road, RoadsResponseDto.class);
    }

    @Override
    public RoadsResponseDto disActive(UUID roadsId) {
        RoadsEntity road = roadsRepository.findById(roadsId).orElseThrow(() -> new DataNotFoundException("Roads not found"));
        road.setIsActive(false);
        roadsRepository.save(road);
        return modelMapper.map(road, RoadsResponseDto.class);
    }



    @Override
    public RoadsResponseDto parse(RoadsEntity roadsEntity) {
        RoadsResponseDto responseDto = new RoadsResponseDto(roadsEntity.getId(), roadsEntity.getDirection());
        responseDto.setId(roadsEntity.getId());
        return responseDto;
    }


    public List<RoadsResponseDto> parse1(List<RoadsEntity> roadsEntity) {
        List<RoadsResponseDto> list =  new ArrayList<>();
        for (RoadsEntity entity : roadsEntity) {
            RoadsResponseDto responseDto = new RoadsResponseDto(entity.getId(), entity.getDirection());
            responseDto.setId(entity.getId());
            list.add(responseDto);
        }

        return list;
    }

    private List<StationResponseDto> parse(List<StationRoadsEntity> stations) {
        List<StationResponseDto> list = new ArrayList<>();
        for (StationRoadsEntity station : stations) {
            list.add(new StationResponseDto(
                    station.getId(),
                    station.getRoad().getDirection(),
                    station.getCreatedDate()));
        }
        return list;
    }


}
