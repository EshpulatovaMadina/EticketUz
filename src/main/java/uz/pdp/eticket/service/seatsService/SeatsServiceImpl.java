package uz.pdp.eticket.service.seatsService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.SeatsCreateDto;
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
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RequiredArgsConstructor
@Service
public class SeatsServiceImpl implements SeatsService{
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
        SeatEntity seatEntity = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found !!!"));
        seatEntity.setIsActive(false);
        seatsRepository.save(seatEntity);
        return "Successfully";
    }

//    @Override
//    public SeatsResponseDto update(UUID seatId, SeatsCreateDto dto) {
//        SeatsEntity seatsEntity = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found !!!"));
//
//        ///numberini ham set qil garang.
//        seatsEntity.setLocation(dto.getLocation());
//        return null;
//    }

    @Override
    public SeatsResponseDto getById(UUID seatId) {
        SeatEntity seatEntity = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found !!!"));
        return parse(seatEntity);
    }

    @Override
    public List<SeatsResponseDto> getSeatsOfVagon(UUID vagonId) {
        List<SeatEntity> allByVagonId = seatsRepository.findAllByVagonId(vagonId);
        return parse(allByVagonId);
    }
    private List<SeatsResponseDto> parse(List<SeatEntity> entities){
        List<SeatsResponseDto> list = new ArrayList<>();
        for (SeatEntity entity : entities) {
            SeatsResponseDto map = modelMapper.map(entity, SeatsResponseDto.class);
            map.setVagonId(entity.getVagonId().getId());
            list.add(map);
        }
        return list;
    }

    private SeatEntity parse(SeatsCreateDto seatsCreateDto){
        return modelMapper.map(seatsCreateDto, SeatEntity.class);
    }

    @Override
    public SeatsResponseDto parse(SeatEntity seatEntity) {
        SeatsResponseDto map = modelMapper.map(seatEntity, SeatsResponseDto.class);
        map.setVagonId(seatEntity.getVagonId().getId());
        return map;
    }

}
