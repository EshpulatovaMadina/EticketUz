package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.ReysCreateDto;
import uz.pdp.eticket.DTO.response.ReysResponseDto;
import uz.pdp.eticket.DTO.response.RoadsResponseDto;
import uz.pdp.eticket.service.reysService.ReysService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/reys")
public class ReysController {
    private final ReysService reysService;

    @Operation(
            description = "This method is used to add reys",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ReysResponseDto> create(@RequestBody ReysCreateDto dto){
        return ResponseEntity.ok(reysService.create(dto));
    }


    @Operation(
            description = "This method return all reys",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ReysResponseDto>> getAll(@RequestParam(value = "page", defaultValue = "0")
                                                         int page,
                                                         @RequestParam(value = "size", defaultValue = "5")
                                                         int size) {
        return ResponseEntity.ok(reysService.getAll(page, size));
    }


    @Operation(
            description = "This method disables the reys",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/dis-active")
    public ResponseEntity<String> disActive(@RequestParam UUID reysId){
        return ResponseEntity.ok(reysService.disActive(reysId));
    }


    @Operation(
            description = "This method get reys by Location", /// 1 chi user kirib locatsiya tanlashdagi korinish uchun bu
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/get-reys-by-location")
    public ResponseEntity<List<ReysResponseDto>> getReysByLocation(
            @RequestParam String fromStation,
            @RequestParam String toStation,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false, defaultValue = "2100-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        // Check if fromDate is null, and provide a default value if needed
        LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : LocalDateTime.MIN;

        // Check if toDate is null, and provide a default value if needed
        LocalDateTime toDateTime = (toDate != null) ? toDate.atStartOfDay() : LocalDateTime.MAX;

        return ResponseEntity.ok(reysService.getReysByLocation(fromStation, toStation, fromDateTime, toDateTime));
    }


}
