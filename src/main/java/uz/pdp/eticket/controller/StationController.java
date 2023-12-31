package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.StationsCreateDto;
import uz.pdp.eticket.DTO.response.StationResponseDto;
import uz.pdp.eticket.DTO.response.VagonResponseDto;
import uz.pdp.eticket.service.stationsService.StationService;

import java.util.List;
import java.util.UUID;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@RequestMapping("/api/v1/station")
public class StationController {
    private final StationService stationService;

    @Operation(
            description = "This method is used to add station",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping()
    public ResponseEntity<StationResponseDto> create(@RequestBody StationsCreateDto stationsCreateDto) {
        return ResponseEntity.ok(stationService.create(stationsCreateDto));
    }


    @Operation(
            description = "this API makes the seat inactive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/dis-active/{stationId}")
    public ResponseEntity<StationResponseDto> disActive(@PathVariable UUID stationId) {
        return ResponseEntity.ok(stationService.disActive(stationId));
    }


    @Operation(
            description = "This method updates the data of one station",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/update/{stationId}")
    public ResponseEntity<StationResponseDto> update(@PathVariable UUID stationId, @RequestBody StationsCreateDto dto) {
        return ResponseEntity.ok(stationService.update(stationId, dto));
    }


    @Operation(
            description = "This method changes the active station",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/is-active/{stationId}")
    public ResponseEntity<StationResponseDto> isActive(@PathVariable UUID stationId) {
        return ResponseEntity.ok(stationService.isActive(stationId));
    }

    @Operation(
            description = "This method returns a single station",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @GetMapping("/by-id/{stationId}")
    public ResponseEntity<StationResponseDto> getbyId(@PathVariable UUID stationId) {
        return ResponseEntity.ok(stationService.getById(stationId));
    }

    @Operation(
            description = "This method return all stations",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<StationResponseDto>> getAll(@RequestParam(value = "page", defaultValue = "0")
                                                         int page,
                                                         @RequestParam(value = "size", defaultValue = "5")
                                                         int size,
                                                         @RequestParam(defaultValue = "") String location) {
        return ResponseEntity.ok(stationService.getAll(page, size, location));
    }
}
