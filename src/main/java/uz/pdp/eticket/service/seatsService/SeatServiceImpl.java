package uz.pdp.eticket.service.seatsService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.response.SeatsResponseDto;
import uz.pdp.eticket.entity.SeatEntity;
import uz.pdp.eticket.entity.VagonEntity;
import uz.pdp.eticket.entity.enums.SeatType;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.SeatsRepository;
import uz.pdp.eticket.repository.VagonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {
    private final SeatsRepository seatsRepository;
    private final ModelMapper modelMapper;
    private final VagonRepository vagonRepository;
    @Override
    public List<SeatsResponseDto> create(UUID vagonId, Double seatPrice) {
        VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(()-> new DataNotFoundException("Vagon not found!!!"));
        List<SeatsResponseDto> list = new ArrayList<>();
        for (int i = 1; i <= vagon.getVagonTypes().getSeats(); i++) {
            if (i % 2 == 0){
                SeatEntity entity = seatsRepository.save(new SeatEntity(seatPrice, i, vagon, SeatType.UP));
                list.add(parse(entity));
            }else {
                SeatEntity entity = seatsRepository.save(new SeatEntity(seatPrice, i, vagon, SeatType.DOWN));
                list.add(parse(entity));
            }
        }
        return list;
    }

    @Override
    public SeatsResponseDto isActive(UUID seatId) {
        SeatEntity seat = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found"));
            seat.setIsActive(true);
            seatsRepository.save(seat);
            return parse(seat);
    }

    @Override
    public String deActive(UUID seatId) {
        SeatEntity seat = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found !!!"));
        seat.setIsActive(false);
        seatsRepository.save(seat);
        return "Successfully";
    }

    @Override
    public SeatsResponseDto getById(UUID seatId) {
        return parse(seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found !!!")));

    }

    @Override
    public List<SeatsResponseDto> getSeatsOfVagon(UUID vagonId) {
        List<SeatEntity> vagonId1 = seatsRepository.findAllByVagonId(vagonId);
        return parse(vagonId1);
    }

    private List<SeatsResponseDto> parse(List<SeatEntity> seats){
        List<SeatsResponseDto> list = new ArrayList<>();
        for (SeatEntity seat : seats) {
            SeatsResponseDto map = modelMapper.map(seat, SeatsResponseDto.class);
            map.setVagonId(seat.getVagonId().getId());
            list.add(map);
        }
        return list;
    }

    @Override
    public SeatsResponseDto parse(SeatEntity seat) {
        SeatsResponseDto map = modelMapper.map(seat, SeatsResponseDto.class);
        map.setVagonId(seat.getVagonId().getId());
        return map;
    }

    @Override
    public SeatEntity findById(UUID seatId) {
        return seatsRepository.findById(seatId)
                .orElseThrow(() -> new DataNotFoundException("Seat not found with id: "+ seatId));
    }

}
