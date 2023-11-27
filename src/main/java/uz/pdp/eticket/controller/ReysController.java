package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.ReysCreateDto;
import uz.pdp.eticket.DTO.response.ReysResponseDto;
import uz.pdp.eticket.service.reysService.ReysService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reys")
public class ReysController {
    private ReysService reysService;

    @Operation(
            description = "This method is used to add reys",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ReysResponseDto> create(@RequestBody ReysCreateDto dto){
        return ResponseEntity.ok(reysService.create(dto));
    }

    @Operation(
            description = "This method disables the reys",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/deActive")
    public ResponseEntity<String> deActive(@RequestParam UUID reysId){
        return ResponseEntity.ok(reysService.deActive(reysId));
    }


    @Operation(
            description = "This method get reys by Location", /// 1 chi user kirib locatsiya tanlashdagi korinish uchun bu
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasAuthority('USER') or hasRole('ADMIN')")
    @GetMapping("/getReysByLocation")
    public ResponseEntity<List<ReysResponseDto>> getReysByLocation(
            @RequestParam String fromStation,
            @RequestParam String toStation,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate ){

       return ResponseEntity.ok(reysService.getReysByLocation(fromStation, toStation, fromDate.atStartOfDay(), toDate.atStartOfDay()));
    }

}
