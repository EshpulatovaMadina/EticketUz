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

import java.security.Principal;
import java.util.List;
import java.util.UUID;

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
    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping("/create")
    public ResponseEntity<CardResponseDTO> add(@RequestBody CardCreateDTO dto, Principal principal){
        return ResponseEntity.ok(cardService.add(dto, UUID.fromString(principal.getName())));
    }



    @Operation(
            description = "This method is return cards of user",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('USER')")
    @GetMapping("/get-cards-of-user")
    public ResponseEntity<List<CardResponseDTO>> getCardsOfUser(Principal principal){
        return ResponseEntity.ok(cardService.getCardsOfUser(UUID.fromString(principal.getName())));
    }


    @Operation(
            description = "this API makes the card delete",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam UUID cardId){
        return ResponseEntity.ok(cardService.delete(cardId));
    }
}
