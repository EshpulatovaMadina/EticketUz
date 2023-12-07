package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.LocomotiveCreateDto;
import uz.pdp.eticket.DTO.response.LocomotiveResponseDto;
import uz.pdp.eticket.DTO.response.ReysResponseDto;
import uz.pdp.eticket.service.locomotiveService.LocomotiveService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/locomative")
public class LocomotiveController {
    private final LocomotiveService locomotiveService;

    @Operation(
            description = "This method is used to add locomotive",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<LocomotiveResponseDto> create(@RequestBody LocomotiveCreateDto locomotiveCreateDto) {
        return ResponseEntity.ok(locomotiveService.create(locomotiveCreateDto));
    }

    @Operation(
            description = "This method return all locomotives",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<LocomotiveResponseDto>> getAll(@RequestParam(value = "page", defaultValue = "0")
                                                        int page,
                                                        @RequestParam(value = "size", defaultValue = "5")
                                                        int size) {
        return ResponseEntity.ok(locomotiveService.getAll(page, size));
    }


    @Operation(
            description = "This method activates the locomotive",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/is-active")
    public ResponseEntity<LocomotiveResponseDto> isActive(@RequestParam UUID locoId) {
        return ResponseEntity.ok(locomotiveService.isActive(locoId));
    }


    @Operation(
            description = "this API makes the locomotive disActive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/disActive")
    public ResponseEntity<String> disActive(@RequestParam UUID locomotiveId) {
        return ResponseEntity.ok(locomotiveService.isInActive(locomotiveId));
    }


    @Operation(
            description = "This method returns a single locomotive",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @GetMapping("/get-by-id")
    public ResponseEntity<LocomotiveResponseDto> getbyId(@RequestParam UUID seatId) {
        return ResponseEntity.ok(locomotiveService.getById(seatId));
    }


    @Operation(
            description = "This method updates the data of one locomotive",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<LocomotiveResponseDto> update(@RequestBody LocomotiveCreateDto dto, @RequestParam UUID locomotiveId) {
        return ResponseEntity.ok(locomotiveService.update(locomotiveId, dto));
    }


}
