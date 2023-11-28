package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.CardCreateDTO;
import uz.pdp.eticket.DTO.response.CardResponseDTO;
import uz.pdp.eticket.service.cardService.CardService;

import java.util.List;
import java.util.UUID;

/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RequestMapping("/api/card")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CardController {
    private final CardService cardService;

    @Operation(
            description = "This method is used to add card",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<CardResponseDTO> add(@RequestBody CardCreateDTO dto){
        return ResponseEntity.ok(cardService.add(dto));
    }



    @Operation(
            description = "This method is return cards of user",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasRole('USER')")
    @GetMapping("/getCardsOfUser/{userId}")
    public ResponseEntity<List<CardResponseDTO>> getCardsOfUser(@PathVariable UUID userId){
        return ResponseEntity.ok(cardService.getCardsOfUser(userId));
    }


    @Operation(
            description = "this API makes the card delete",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id){
        return ResponseEntity.ok(cardService.delete(id));
    }
}
