package uz.pdp.eticket.service.roadsService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.RoadsCreateDto;
import uz.pdp.eticket.DTO.response.RoadsResponseDto;
import uz.pdp.eticket.DTO.response.StationResponseDto;
import uz.pdp.eticket.entity.RoadsEntity;
import uz.pdp.eticket.entity.StationRoadsEntity;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.RoadsRepository;
import uz.pdp.eticket.repository.StationRoadsRepository;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoadsServiceImpl implements RoadsService {
    private final RoadsRepository roadsRepository;
    private final ModelMapper modelMapper;
    private final StationRoadsService stationRoadsService;
    private final StationRoadsRepository sr;

    @Override
    public RoadsResponseDto create(RoadsCreateDto roadsCreateDto) {
        RoadsEntity parse = parse(roadsCreateDto);
        if (roadsRepository.existsByDirection(parse.getDirection())) {
            throw new DataAlreadyExistsException("This Road name already exists . Please can you create other name ?");
        }
        RoadsEntity save = roadsRepository.save(parse);
        if (roadsCreateDto.getStations() != null && !roadsCreateDto.getStations().isEmpty()) {
            stationRoadsService.save(save.getId(), roadsCreateDto.getStations());
        }
        return new RoadsResponseDto(save.getId(), save.getDirection());
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
    }

    private List<StationResponseDto> parse(List<StationRoadsEntity> stations) {
        List<StationResponseDto> list = new ArrayList<>();

        for (int i = 1; i < stations.size()-1; i++) {
            StationRoadsEntity s = stations.get(i);
            list.add(new StationResponseDto(
                    s.getStation().getId(),
                    s.getStation().getName(),
                    s.getStation().getLocation(),
                    new RoadsResponseDto(s.getRoad().getId(), s.getRoad().getDirection()),
                    null,
                    null,
                    s.getCreatedDate()
                    ));
        }
        return list;
    }


    /**
     * hullas bu method 2 ta stansiya qaysi yonalishda uchrasa shu yonalishni nameini qaytaradi
     * @return
     */
//  @Override
////    public String getDirectionByStation(String fromStation, String toStation) {
////        return roadsRepository.findAllDirectionByStations(fromStation, toStation);
////    }
    @Override
    public RoadsResponseDto parse(RoadsEntity roadsEntity) {
        RoadsResponseDto responseDto = new RoadsResponseDto(roadsEntity.getId(), roadsEntity.getDirection());
        responseDto.setId(roadsEntity.getId());
        return responseDto;
    }

    @Override
    public RoadsResponseDto getById(UUID roadsId) {
        RoadsEntity road = roadsRepository.findById(roadsId).orElseThrow(() -> new DataNotFoundException("Roads not found"));
        return modelMapper.map(road, RoadsResponseDto.class);
    }

    @Override
    public RoadsResponseDto isActive(UUID roadsId) {
        RoadsEntity road = roadsRepository.findById(roadsId).orElseThrow(() -> new DataNotFoundException("Roads not found"));
        road.setIsActive(true);
        roadsRepository.save(road);
        return modelMapper.map(road, RoadsResponseDto.class);
    }

    @Override
    public RoadsResponseDto deActive(UUID roadsId) {
        RoadsEntity road = roadsRepository.findById(roadsId).orElseThrow(() -> new DataNotFoundException("Roads not found"));
        road.setIsActive(false);
        roadsRepository.save(road);
        return modelMapper.map(road, RoadsResponseDto.class);
    }

    private RoadsEntity parse(RoadsCreateDto dto) {
        return modelMapper.map(dto, RoadsEntity.class);
    }


}
