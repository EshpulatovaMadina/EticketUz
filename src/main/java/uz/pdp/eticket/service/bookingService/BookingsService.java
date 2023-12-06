package uz.pdp.eticket.service.bookingService;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.eticket.DTO.request.BookingCreateDto;
import uz.pdp.eticket.DTO.response.BookingsResponseDto;

import java.util.List;
import java.util.UUID;

@Service
public interface BookingsService {
    BookingsResponseDto create(BookingCreateDto dto,UUID userId);

    BookingsResponseDto getById(UUID bookingId);

    Boolean getByReys(UUID id);
    String checkExcpiryDate();

    Boolean ticketIsSoldOrNot(UUID seatId, UUID reysId);

    List<BookingsResponseDto> getBookingOfUser(UUID userId);

    InputStreamResource getQRCode(UUID ticketId);
}
