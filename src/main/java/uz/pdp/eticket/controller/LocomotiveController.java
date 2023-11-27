package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.LocomotiveCreateDto;
import uz.pdp.eticket.DTO.response.LocomotiveResponseDto;
import uz.pdp.eticket.service.locomotiveService.LocomotiveService;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locomative")
public class LocomotiveController {
    private final LocomotiveService locomotiveService;
    @Operation(
            description = "This method is used to add locomotive",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<LocomotiveResponseDto> create(@RequestBody LocomotiveCreateDto locomotiveCreateDto){
        return ResponseEntity.ok(locomotiveService.create(locomotiveCreateDto));
    }



    @Operation(
            description = "This method activates the locomotive",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/isActive")
    public ResponseEntity<LocomotiveResponseDto> isActive(@RequestParam UUID locoId){
        return ResponseEntity.ok(locomotiveService.isActive(locoId));
    }


    @Operation(
            description = "this API makes the locomotive disActive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/disActive")
    public ResponseEntity<String> disActive(@RequestParam UUID locomotiveId){
        return ResponseEntity.ok(locomotiveService.isInActive(locomotiveId));
    }


    @Operation(
            description = "This method returns a single locomotive",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getById")
    public ResponseEntity<LocomotiveResponseDto> getbyId(@RequestParam UUID seatId){
        return ResponseEntity.ok(locomotiveService.getById(seatId));
    }



    @Operation(
            description = "This method updates the data of one locomotive",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize"  , scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<LocomotiveResponseDto> update(@RequestBody LocomotiveCreateDto dto, @RequestParam UUID locomotiveId){
        return ResponseEntity.ok(locomotiveService.update(locomotiveId, dto));
    }



}
