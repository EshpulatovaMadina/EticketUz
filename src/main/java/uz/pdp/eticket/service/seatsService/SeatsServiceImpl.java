package uz.pdp.eticket.service.seatsService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.SeatsCreateDto;
import uz.pdp.eticket.DTO.response.SeatsResponseDto;
import uz.pdp.eticket.entity.SeatsEntity;
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
                SeatsEntity entity = seatsRepository.save(new SeatsEntity(seatPrice, i, vagon, SeatType.UP));
                list.add(parse(entity));
            }else {
                SeatsEntity entity = seatsRepository.save(new SeatsEntity(seatPrice, i, vagon, SeatType.DOWN));
                list.add(parse(entity));
            }
        }
        return list;
    }

    @Override
    public SeatsResponseDto isActive(UUID seatId) {
        SeatsEntity seat = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found"));
            seat.setIsActive(true);
            seatsRepository.save(seat);
            return parse(seat);
    }

    @Override
    public String deActive(UUID seatId) {
        SeatsEntity seatsEntity = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found !!!"));
        seatsEntity.setIsActive(false);
        seatsRepository.save(seatsEntity);
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
        SeatsEntity seatsEntity = seatsRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found !!!"));
        return parse(seatsEntity);
    }

    @Override
    public List<SeatsResponseDto> getSeatsOfVagon(UUID vagonId) {
        List<SeatsEntity> allByVagonId = seatsRepository.findAllByVagonId(vagonId);
        return parse(allByVagonId);
    }
    private List<SeatsResponseDto> parse(List<SeatsEntity> entities){
        List<SeatsResponseDto> list = new ArrayList<>();
        for (SeatsEntity entity : entities) {
            SeatsResponseDto map = modelMapper.map(entity, SeatsResponseDto.class);
            map.setVagonId(entity.getVagonId().getId());
            list.add(map);
        }
        return list;
    }

    private SeatsEntity parse(SeatsCreateDto seatsCreateDto){
        return modelMapper.map(seatsCreateDto, SeatsEntity.class);
    }

    @Override
    public SeatsResponseDto parse(SeatsEntity seatsEntity) {
        SeatsResponseDto map = modelMapper.map(seatsEntity, SeatsResponseDto.class);
        map.setVagonId(seatsEntity.getVagonId().getId());
        return map;
    }

}
