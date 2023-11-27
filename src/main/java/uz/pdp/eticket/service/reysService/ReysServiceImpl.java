package uz.pdp.eticket.service.reysService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.eticket.DTO.request.ReysCreateDto;
import uz.pdp.eticket.DTO.response.ReysResponseDto;
import uz.pdp.eticket.entity.ReysEntity;
import uz.pdp.eticket.exception.CannotBeChangedException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.ReysRepository;
import uz.pdp.eticket.repository.StationRoadsRepository;
import uz.pdp.eticket.service.bookingService.BookingsService;
import uz.pdp.eticket.service.roadsService.RoadsService;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Service
@RequiredArgsConstructor
public class ReysServiceImpl implements ReysService{
    String fooResourceUrl = "STATION_SERVICE";
    private BookingsService bookingsService;
    private StationRoadsService stationRoadsService;
    private ReysRepository reysRepository;
    private ModelMapper modelMapper;


    @Override
    public List<ReysResponseDto> getReysByLocation(String fromStation, String toStation, LocalDateTime fromDate, LocalDateTime toDate) {
        List<String> directions = stationRoadsService.findAllDirectionByStations(fromStation, toStation);
        List<ReysResponseDto> parse = new ArrayList<>();
        for (String direction : directions) {
            List<ReysEntity> allByDirectionAndFromDate = reysRepository.findAllByDirectionAndFromDate(direction, fromDate);
            parse.addAll(parse(allByDirectionAndFromDate));
        }
        return parse;
    }

    @Override
    public ReysResponseDto create(ReysCreateDto dto) {
        ReysEntity map = modelMapper.map(dto, ReysEntity.class);
        reysRepository.save(map);
        return parse(map);
    }

    @Override
    public String deActive(UUID reysId) {
        ReysEntity reysNotFound = reysRepository.findById(reysId).orElseThrow(() -> new DataNotFoundException("Reys not found"));
        Boolean bool =   bookingsService.getByReys(reysNotFound.getId());
        if (bool){
            throw new CannotBeChangedException("Cannot be changed because tickets are sold out on this flight ");
        }
          reysNotFound.setIsActive(false);
        return "Successfully";
    }

    private ReysResponseDto parse(ReysEntity entity){
        ReysResponseDto map = modelMapper.map(entity, ReysResponseDto.class);
        map.setReysId(entity.getId());
        map.setCreateDate(entity.getCreatedDate());
        return map;
    }

    private List<ReysResponseDto> parse(List<ReysEntity> entities){
        List<ReysResponseDto> list = new ArrayList<>();
        for (ReysEntity entity : entities) {
            ReysResponseDto map = modelMapper.map(entity, ReysResponseDto.class);
            map.setReysId(entity.getId());
            map.setCreateDate(entity.getCreatedDate());
            list.add(map);
        }
        return list;
    }


}
