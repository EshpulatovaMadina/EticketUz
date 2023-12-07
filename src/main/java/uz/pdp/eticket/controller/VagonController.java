package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.VagonCreateDto;
import uz.pdp.eticket.DTO.response.FreeVagonResponseDto;
import uz.pdp.eticket.DTO.response.VagonResponseDto;
import uz.pdp.eticket.service.vagonService.VagonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/vagon")
public class VagonController {
    private final VagonService vagonService;

    @Operation(
            description = "This method is used to add a wagon",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<List<VagonResponseDto>> create(@RequestBody List<VagonCreateDto> vagonCreateDtos, @RequestParam Double seatPrice) {////buyerga price kirib kelsin deganim bitta vagon ichoda seatlar default yaratiladi shuning un narhi vagon typega bog'liq bolganligi un pri vagon typelar bn birga admin bersin dedim
        return ResponseEntity.ok(vagonService.create(vagonCreateDtos, seatPrice));
    }


    @Operation(
            description = "This method return all vagons",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<VagonResponseDto>> getAll(@RequestParam(value = "page", defaultValue = "0")
                                                             int page,
                                                         @RequestParam(value = "size", defaultValue = "5")
                                                          int size) {
        return ResponseEntity.ok(vagonService.getAll(page, size));
    }

    @Operation(
            description = "this API makes the wagon inactive",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam UUID vagonId) {
        return ResponseEntity.ok(vagonService.disActive(vagonId));
    }


    @Operation(
            description = "This method changes the active wagon",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("/isActiveUpdate")
    public ResponseEntity<VagonResponseDto> isActiveUpdate(@RequestParam UUID vagonId) {
        return ResponseEntity.ok(vagonService.isActive(vagonId));
    }

    @Operation(
            description = "This method returns all wagon of a single locomotive",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @GetMapping("/get-vagons-of-locomotive")
    public ResponseEntity<List<VagonResponseDto>> getVagonsOfLocomotive(@RequestParam UUID locomotiveId) {
        return ResponseEntity.ok(vagonService.getVagonsOfLocomotive(locomotiveId));
    }


    @Operation(
            description = "This method returns a single wagon",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @GetMapping("/getById")
    public ResponseEntity<VagonResponseDto> getbyId(@RequestParam UUID vagonId) {
        return ResponseEntity.ok(vagonService.getById(vagonId));
    }

    @Operation(
            description = "This method returns a free wagons of one locomotive",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/getFreeVagon")
    public ResponseEntity<List<FreeVagonResponseDto>> getFreeVagons(@RequestParam UUID locomotiveId, @RequestParam UUID reysId) {
        return ResponseEntity.ok(vagonService.getFreeVagon(locomotiveId, reysId));
    }

    @Operation(
            description = "This method connects one wagon to a locomotive",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/setLocomotive")
    public ResponseEntity<List<VagonResponseDto>> setLocomotive(@RequestParam List<UUID> vagonsId, @RequestParam UUID locomotiveId) {
        return ResponseEntity.ok(vagonService.setLocomotive(vagonsId, locomotiveId));
    }

}

