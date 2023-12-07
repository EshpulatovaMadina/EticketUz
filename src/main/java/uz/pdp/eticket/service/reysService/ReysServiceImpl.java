package uz.pdp.eticket.service.reysService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.eticket.DTO.request.MailDto;
import uz.pdp.eticket.DTO.request.ReysCreateDto;
import uz.pdp.eticket.DTO.response.ReysResponseDto;
import uz.pdp.eticket.entity.LocomotiveEntity;
import uz.pdp.eticket.entity.ReysEntity;
import uz.pdp.eticket.entity.RoadsEntity;
import uz.pdp.eticket.entity.UserEntity;
import uz.pdp.eticket.entity.enums.Direction;
import uz.pdp.eticket.exception.CannotBeChangedException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.ReysRepository;
import uz.pdp.eticket.repository.StationRoadsRepository;
import uz.pdp.eticket.repository.UserRepository;
import uz.pdp.eticket.service.MailService;
import uz.pdp.eticket.service.bookingService.BookingsService;
import uz.pdp.eticket.service.locomotiveService.LocomotiveService;
import uz.pdp.eticket.service.roadsService.RoadsService;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReysServiceImpl implements ReysService{
    private final BookingsService bookingsService;
    private final StationRoadsService stationRoadsService;
    private final ReysRepository reysRepository;
    private final ModelMapper modelMapper;
    private final RoadsService roadsService;
    private final LocomotiveService locomotiveService;


    @Override
    public List<ReysResponseDto> getReysByLocation(String fromStation, String toStation, LocalDateTime fromDate, LocalDateTime toDate) {

        List<String> directions = stationRoadsService.findAllDirectionByStations(fromStation, toStation);
        List<ReysResponseDto> parse = new ArrayList<>();
        for (String direction : directions) {
                List<ReysEntity> all = reysRepository.findAllByDirectionAndStartDate(direction, fromDate);
                parse.addAll(parse(all));

        }
        return parse;
    }

    @Override
    public ReysResponseDto create(ReysCreateDto dto) {
        RoadsEntity road = roadsService.findById(dto.getRoadsId());
        LocomotiveEntity locomotive = locomotiveService.findById(dto.getLocomotiveId());
        ReysEntity reys = new ReysEntity(road, dto.getDirection(), dto.getFromStation(), dto.getToStation(), locomotive, dto.getStartDate());
        reysRepository.save(reys);
        return parse(reys);
    }

    @Override
    public String disActive(UUID reysId) {
        ReysEntity reysNotFound = reysRepository.findById(reysId).orElseThrow(() -> new DataNotFoundException("Reys not found"));
        Boolean bool =   bookingsService.getByReys(reysNotFound.getId());
        if (bool){
            throw new CannotBeChangedException("Cannot be changed because tickets are sold out on this flight ");
        }
          reysNotFound.setIsActive(false);
        return "Successfully";
    }



    @Override
    public ReysEntity findById(UUID reysId) {
        return reysRepository.findById(reysId)
                .orElseThrow(()-> new DataNotFoundException("Reys not found with id: "+reysId));
    }

    @Override
    public List<ReysResponseDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ReysEntity> roadPage = reysRepository.findAllByIsActiveTrue(pageRequest);
        List<ReysEntity> content = roadPage.getContent();
        return parse(content);
    }

    private ReysResponseDto parse(ReysEntity entity) {
        return new ReysResponseDto(
                entity.getId(),
                entity.getRoadsId().getId(),
                Direction.valueOf(entity.getDirection()),
                entity.getFromStation(),
                entity.getToStation(),
                entity.getLocomotiveId().getId(),
                entity.getStartDate(),
                entity.getCreatedDate()
        );
    }

    private List<ReysResponseDto> parse(List<ReysEntity> entities) {
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
