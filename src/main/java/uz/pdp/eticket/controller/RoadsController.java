package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.RoadsCreateDto;
import uz.pdp.eticket.DTO.response.RoadsResponseDto;
import uz.pdp.eticket.service.roadsService.RoadsService;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roads")
public class RoadsController {
    private RoadsService roadsService;
    @Operation(
            description = "This method is used to add roads",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RoadsResponseDto> create(@RequestBody RoadsCreateDto roadsCreateDto){
        return ResponseEntity.ok(roadsService.create(roadsCreateDto));
    }


    @Operation(
            description = "this API makes the road inactive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/deActive")
    public ResponseEntity<RoadsResponseDto> deActive(@RequestParam UUID roadsId){
        return ResponseEntity.ok(roadsService.deActive(roadsId));
    }

    @Operation(
            description = "",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getByDirection")
    public ResponseEntity<RoadsResponseDto> getRoadByDirection(@RequestParam String direction){
        return ResponseEntity.ok(roadsService.getByDirection(direction));
    }


    @Operation(
            description = "This method updates the data of one roads",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<RoadsResponseDto> update(@RequestParam UUID roadsId, @RequestBody RoadsCreateDto dto){
        return ResponseEntity.ok(roadsService.update(roadsId, dto));
    }


    @Operation(
            description = "This method changes the active roads",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/isActive")
    public ResponseEntity<RoadsResponseDto> isActive(@RequestParam UUID roadsId){
        return ResponseEntity.ok(roadsService.isActive(roadsId));
    }

    @Operation(
            description = "This method returns a single roads",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getById")
    public ResponseEntity<RoadsResponseDto> getbyId(@RequestParam UUID roadsId){
        return ResponseEntity.ok(roadsService.getById(roadsId));
    }

}
