package uz.pdp.eticket.service.bookingService;
import uz.pdp.eticket.DTO.request.BookingCreateDto;
import uz.pdp.eticket.DTO.response.BookingsResponseDto;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface BookingsService {
    BookingsResponseDto create(BookingCreateDto dto);

    BookingsResponseDto getById(UUID bookingId);

    Boolean getByReys(UUID id);

    Boolean ticketIsSoldOrNot(UUID seatId, UUID reysId);

    List<BookingsResponseDto> getBookingOfUser(UUID userId);

}
