package uz.pdp.eticket.service.seatsService;

import uz.pdp.eticket.DTO.response.SeatsResponseDto;
import uz.pdp.eticket.entity.SeatEntity;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    List<SeatsResponseDto> create(UUID vagonId, Double seatPrice);
    SeatsResponseDto isActive(UUID seatId);
    String deActive(UUID seatId);
//    SeatsResponseDto update(UUID seatId, SeatsCreateDto dto);
    SeatsResponseDto getById(UUID seatId);
    List<SeatsResponseDto> getSeatsOfVagon(UUID vagonId);
    SeatsResponseDto parse(SeatEntity seatEntity);

    SeatEntity findById(UUID seatId);
}
