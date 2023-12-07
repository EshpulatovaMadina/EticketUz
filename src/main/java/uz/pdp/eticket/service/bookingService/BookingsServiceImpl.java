package uz.pdp.eticket.service.bookingService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.eticket.DTO.request.BookingCreateDto;
import uz.pdp.eticket.DTO.response.BookingsResponseDto;
import uz.pdp.eticket.entity.*;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.BookingsRepository;
import uz.pdp.eticket.repository.ReysRepository;
import uz.pdp.eticket.repository.VagonRepository;
import uz.pdp.eticket.service.seatsService.SeatService;
import uz.pdp.eticket.service.userService.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingsServiceImpl implements BookingsService{

    private final BookingsRepository bookingsRepository;
    private final ReysRepository reysService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final SeatService seatService;
    private final VagonRepository vagonService;

    @Override
    public BookingsResponseDto create(BookingCreateDto dto, UUID userId) {
        BookingEntity booking = parse(dto,userId);
        bookingsRepository.save(booking);
        return parse(booking);
    }

    @Override
    public BookingsResponseDto getById(UUID bookingId) {
        return parse(bookingsRepository.findById(bookingId).orElseThrow(() -> new DataNotFoundException("Booking not found !!!")));
    }


    @Override
    public Boolean getByReys(UUID reysId) {
        return bookingsRepository.existsAllByReysId(reysId);
    }

    @Override
    @Transactional
    public String checkExcpiryDate() {
         bookingsRepository.updateBookingStatusByCreatedDateBefore(LocalDateTime.now().minusMinutes(10));
         return "Deleted bookings";
    }

    @Override
    public Boolean ticketIsSoldOrNot(UUID seatId, UUID reysId) {
        return bookingsRepository.existsBySeatIdAndReysId(seatId, reysId);
    }

    @Override
    public List<BookingsResponseDto> getBookingOfUser(UUID userId) {
        List<BookingEntity> bookingOfUser = bookingsRepository.findAllByUserId(userId);
        return bookingOfUser.stream().map(this::parse).toList();
    }


    @Override
    public InputStreamResource getQRCode(UUID ticketId) {
                BookingEntity ticket = bookingsRepository.findById(ticketId)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        String text = ticket.toString();

        int width = 300; // QR kodining eni
        int height = 300; // QR kodining bo'yi
        String format = "png"; // QR kod rasmi formati

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(
                    ticket.toString(),
                    BarcodeFormat.QR_CODE,
                    width,
                    height,
                    createHintMap()
            );

            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            byte[] imageBytes = convertImageToByteArray(image);

            // Create InputStreamResource from byte array
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

            return new InputStreamResource(inputStream);
        } catch (WriterException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate and save QR code", e);
        }
    }

    private byte[] convertImageToByteArray(BufferedImage image) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., log it)
            throw new RuntimeException("Failed to convert image to byte array", e);
        }
    }

    private static java.util.Map<EncodeHintType, Object> createHintMap() {
        java.util.Map<EncodeHintType, Object> hintMap = new java.util.HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return hintMap;
    }


    private BookingEntity parse(BookingCreateDto dto,UUID userId) {
        UserEntity userEntity = userService.findById(userId);
        SeatEntity seatEntity = seatService.findById(dto.getSeatId());
        ReysEntity reysEntity = reysService.findById(dto.getReysId()).orElseThrow(()-> new DataNotFoundException("Reys not found"));
        VagonEntity vagonEntity = vagonService.findById(dto.getVagonId()).orElseThrow(()-> new DataNotFoundException("Vagon not found"));

        return new BookingEntity(
                userEntity,
                userEntity.getName(),
                userEntity.getSurname(),
                dto.getIdentity(),
                userEntity.getBirthday(),
                seatEntity,
                dto.getPrice(),
                reysEntity,
                vagonEntity,
                dto.getDate()
        );
    }

    private BookingsResponseDto parse(BookingEntity booking){
        BookingsResponseDto map = modelMapper.map(booking, BookingsResponseDto.class);
        map.setBookingId(booking.getId());
        map.setCreatedDate(booking.getCreatedDate());
        return map;
    }

}
