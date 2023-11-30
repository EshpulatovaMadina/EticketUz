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
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.eticket.DTO.request.BookingCreateDto;
import uz.pdp.eticket.DTO.response.BookingsResponseDto;
import uz.pdp.eticket.entity.BookingsEntity;
import uz.pdp.eticket.entity.ReysEntity;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.BookingsRepository;
import uz.pdp.eticket.repository.ReysRepository;
import uz.pdp.eticket.repository.UserRepository;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingsServiceImpl implements BookingsService{

    private final BookingsRepository bookingsRepository;
    private final ReysRepository reysRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

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


    @Override
    public InputStreamResource getQRCode(UUID ticketId) {
                BookingsEntity ticket = bookingsRepository.findById(ticketId)
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
            InputStreamResource resource = new InputStreamResource(inputStream);

            // Set headers

            // Return the ResponseEntity with headers, status, and resource
            return resource;
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
