package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.response.SeatsResponseDto;
import uz.pdp.eticket.service.seatsService.SeatsService;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seat")
public class SeatsController {
    private final SeatsService seatsService;
    @Operation(
            description = "This method is used to add a seat",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<List<SeatsResponseDto>> create(@RequestBody UUID vagonId, @RequestParam Double seatPrice){
        return ResponseEntity.ok(seatsService.create(vagonId,seatPrice ));
    }


    @Operation(
            description = "this API makes the seat inactive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/deActive")
    public ResponseEntity<String> deActice(@RequestParam UUID seatId){
        return ResponseEntity.ok(seatsService.deActive(seatId));
    }


//    @Operation(
//            description = "This method updates the data of one seat",
//            method = "PUT method is supported",
//            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
//    )
//    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
//    @PutMapping("/update")
//    public ResponseEntity<SeatsResponseDto> update(@RequestParam UUID seatId, @RequestBody SeatsCreateDto dto){
//        return ResponseEntity.ok(seatsService.(seatId, dto));
//    }


    @Operation(
            description = "This method changes the active seat",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/isActiveUpdate")
    public ResponseEntity<SeatsResponseDto> isActive(@RequestParam UUID seatId){
        return ResponseEntity.ok(seatsService.isActive(seatId));
    }

    @Operation(
            description = "This method returns a single seat",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getById")
    public ResponseEntity<SeatsResponseDto> getbyId(@RequestParam UUID seatId){
        return ResponseEntity.ok(seatsService.getById(seatId));
    }


    @Operation(
            description = "this API is used to get all the seats of the carriage",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getSeatsOfVagon")
    public ResponseEntity<List<SeatsResponseDto>> getSeatsOfVagon(@RequestParam UUID vagonId ){
        return ResponseEntity.ok(seatsService.getSeatsOfVagon(vagonId));
    }


}
