package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.eticket.DTO.request.BookingCreateDto;
import uz.pdp.eticket.DTO.response.BookingsResponseDto;
import uz.pdp.eticket.service.bookingService.BookingsService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
@SecurityRequirement(name = "Bearer Authentication")
@MultipartConfig(
        maxFileSize = 1024*1024*10, // 10Mb
        fileSizeThreshold = 1024*20, // 20Kb (kilo bytes)
        maxRequestSize = 1024*1024*20 // 20Mb
)
public class BookingsController {
    private final BookingsService bookingsService;
    @Operation(
            description = "This method is used to add booking",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('USER')") /// shuyerini sorash kk user boooking create qiladimi  ozi
    @PostMapping("/create")
    public ResponseEntity<BookingsResponseDto> create(@Valid @RequestBody BookingCreateDto dto, Principal principal){
        return ResponseEntity.ok(bookingsService.create(dto,UUID.fromString(principal.getName())));
    }

    @Operation(
            description = "This method returns a single booking",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/get-by-id")
    public ResponseEntity<BookingsResponseDto> getById(@RequestParam UUID bookingId){
        return ResponseEntity.ok(bookingsService.getById(bookingId));
    }

    @Operation(
            description = "This method returns bookings of user",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('USER')")
    @PutMapping("/get-booking-of-user")
    public ResponseEntity<List<BookingsResponseDto>> getBookingOfUser(Principal principal){
        return ResponseEntity.ok(bookingsService.getBookingOfUser(UUID.fromString(principal.getName())));
    }



    @Operation(
            description = "This method returns whether the ticket has been sold or not",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/ticket-is-sold-or-not")
    public ResponseEntity<Boolean> ticketIsSoldOrNot(@RequestParam UUID seatId,@RequestParam UUID reysId ){
        return ResponseEntity.ok(bookingsService.ticketIsSoldOrNot(seatId, reysId));
    }

    @PermitAll
    @GetMapping("/qr-code")
    public ResponseEntity<InputStreamResource> getQRCode(@RequestParam UUID ticketId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=qr-code.png");
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .contentType(MediaType.IMAGE_PNG)
                .body(bookingsService.getQRCode(ticketId));
    }
}
