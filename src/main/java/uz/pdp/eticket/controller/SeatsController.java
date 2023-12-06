package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.response.SeatsResponseDto;
import uz.pdp.eticket.service.seatsService.SeatService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/seat")
public class SeatsController {
    private final SeatService seatService;
    @Operation(
            description = "This method is used to add a seat",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<List<SeatsResponseDto>> create(@RequestBody UUID vagonId, @RequestParam Double seatPrice){
        return ResponseEntity.ok(seatService.create(vagonId,seatPrice ));
    }


    @Operation(
            description = "this API makes the seat inactive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/dis-active")
    public ResponseEntity<String> disActive(@RequestParam UUID seatId){
        return ResponseEntity.ok(seatService.deActive(seatId));
    }

    @Operation(
            description = "This method changes the active seat",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/is-active")
    public ResponseEntity<SeatsResponseDto> isActive(@RequestParam UUID seatId){
        return ResponseEntity.ok(seatService.isActive(seatId));
    }

    @Operation(
            description = "This method returns a single seat",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/get-by-id")
    public ResponseEntity<SeatsResponseDto> getbyId(@RequestParam UUID seatId){
        return ResponseEntity.ok(seatService.getById(seatId));
    }


    @Operation(
            description = "this API is used to get all the seats of the carriage",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/get-seats-of-vagon")
    public ResponseEntity<List<SeatsResponseDto>> getSeatsOfVagon(@RequestParam UUID vagonId ){
        return ResponseEntity.ok(seatService.getSeatsOfVagon(vagonId));
    }


}
