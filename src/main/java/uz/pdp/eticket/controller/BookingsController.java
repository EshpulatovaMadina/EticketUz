package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.BookingCreateDto;
import uz.pdp.eticket.DTO.response.BookingsResponseDto;
import uz.pdp.eticket.service.bookingService.BookingsService;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
@SecurityRequirement(name = "Bearer Authentication")
public class BookingsController {
    private BookingsService bookingsService;
    @Operation(
            description = "This method is used to add booking",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasRole('USER')") /// shuyerini sorash kk user boooking create qiladimi  ozi
    @PostMapping("/create")
    public ResponseEntity<BookingsResponseDto> create(@Valid @RequestBody BookingCreateDto dto){
        return ResponseEntity.ok(bookingsService.create(dto));
    }

    @Operation(
            description = "This method returns a single booking",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getById")
    public ResponseEntity<BookingsResponseDto> getById(@RequestParam UUID bookingId){
        return ResponseEntity.ok(bookingsService.getById(bookingId));
    }

    @Operation(
            description = "This method returns bookings of user",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/getBookingOfUser")
    public ResponseEntity<List<BookingsResponseDto>> getBookingOfUser(@RequestParam UUID userId){
        return ResponseEntity.ok(bookingsService.getBookingOfUser(userId));
    }



    @Operation(
            description = "This method returns whether the ticket has been sold or not",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/ticketIsSoldOrNot")
    public ResponseEntity<Boolean> ticketIsSoldOrNot(@RequestParam UUID seatId,@RequestParam UUID reysId ){
        return ResponseEntity.ok(bookingsService.ticketIsSoldOrNot(seatId, reysId));
    }
}
