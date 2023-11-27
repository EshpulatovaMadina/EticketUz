package uz.pdp.eticket.service.bookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.BookingCreateDto;
import uz.pdp.eticket.DTO.response.BookingsResponseDto;
import uz.pdp.eticket.entity.BookingsEntity;
import uz.pdp.eticket.entity.ReysEntity;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.BookingsRepository;
import uz.pdp.eticket.repository.ReysRepository;

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
public class BookingsServiceImpl implements BookingsService{
    private BookingsRepository bookingsRepository;
    private ReysRepository reysRepository;
    private ModelMapper modelMapper;
    @Override
    public BookingsResponseDto create(BookingCreateDto dto) {
        ReysEntity reys = reysRepository.findById(dto.getReysId()).orElseThrow(() -> new DataNotFoundException("Reys not found"));
        BookingsEntity map = modelMapper.map(dto, BookingsEntity.class);
        map.setReys(reys);
        bookingsRepository.save(map);
        return parse(map);
    }

    @Override
    public BookingsResponseDto getById(UUID bookingId) {
        BookingsEntity bookingsEntity = bookingsRepository.findById(bookingId).orElseThrow(() -> new DataNotFoundException("Booking not found !!!"));
        return parse(bookingsEntity);
    }

    @Override
    public Boolean getByReys(UUID reysId) {
        return bookingsRepository.existsAllByReysId(reysId);
    }

    @Override
    public String checkExcpiryDate() {
         bookingsRepository.deleteAllByCreatedDateBefore(LocalDateTime.now().minusMinutes(10));
         return "Delete booking";
    }

    @Override
    public Boolean ticketIsSoldOrNot(UUID seatId, UUID reysId) {
        return bookingsRepository.existsBySeatIdAndReysId(seatId, reysId);
    }

    @Override
    public List<BookingsResponseDto> getBookingOfUser(UUID userId) {
        List<BookingsEntity> allByUserId = bookingsRepository.findAllByUserId(userId);
        return parse(allByUserId);
    }
    private BookingsResponseDto parse(BookingsEntity booking){
        BookingsResponseDto map = modelMapper.map(booking, BookingsResponseDto.class);
        map.setBookingId(booking.getId());
        map.setCreatedDate(booking.getCreatedDate());
        return map;
    }

    private List<BookingsResponseDto> parse(List<BookingsEntity> allByUserId){
        List<BookingsResponseDto> list = new ArrayList<>();
        for (BookingsEntity bookings : allByUserId) {
            BookingsResponseDto map = modelMapper.map(bookings, BookingsResponseDto.class);
            map.setBookingId(bookings.getId());
            map.setCreatedDate(bookings.getCreatedDate());
            list.add(map);
        }
        return list;
    }
}
