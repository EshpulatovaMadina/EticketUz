package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.RoadsCreateDto;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.response.RoadsResponseDto;
import uz.pdp.eticket.DTO.response.StationResponseDto;
import uz.pdp.eticket.service.roadsService.RoadsService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/roads")
public class RoadsController {
    private final RoadsService roadsService;
    @Operation(
            description = "This method is used to add roads",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RoadsResponseDto> create(@RequestBody RoadsCreateDto roadsCreateDto){
        return ResponseEntity.ok(roadsService.create(roadsCreateDto));
    }

    @PutMapping("/set-station")
    public ResponseEntity<RoadsResponseDto> setStation(@RequestParam UUID roadId, @RequestBody List<StationRoadCreateDto> stations) {
        return ResponseEntity.ok(roadsService.setStation(roadId,stations));
    }

    @Operation(
            description = "this API makes the road inactive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/dis-active")
    public ResponseEntity<RoadsResponseDto> disActive(@RequestParam UUID roadsId){
        return ResponseEntity.ok(roadsService.disActive(roadsId));
    }

    @Operation(
            description = "",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @GetMapping("/get-by-direction")
    public ResponseEntity<RoadsResponseDto> getRoadByDirection(@RequestParam String direction){
        return ResponseEntity.ok(roadsService.getByDirection(direction));
    }


    @Operation(
            description = "This method updates the data of one roads",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<RoadsResponseDto> update(@RequestParam UUID roadsId, @RequestBody RoadsCreateDto dto){
        return ResponseEntity.ok(roadsService.update(roadsId, dto));
    }


    @Operation(
            description = "This method changes the active roads",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/is-active")
    public ResponseEntity<RoadsResponseDto> isActive(@RequestParam UUID roadsId){
        return ResponseEntity.ok(roadsService.isActive(roadsId));
    }

    @Operation(
            description = "This method returns a single roads",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @GetMapping("/get-by-id")
    public ResponseEntity<RoadsResponseDto> getbyId(@RequestParam UUID roadsId){
        return ResponseEntity.ok(roadsService.getById(roadsId));
    }


    @Operation(
            description = "This method return all roads",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<RoadsResponseDto>> getAll(@RequestParam(value = "page", defaultValue = "0")
                                                           int page,
                                                           @RequestParam(value = "size", defaultValue = "5")
                                                           int size) {
        return ResponseEntity.ok(roadsService.getAll(page, size));
    }


}
