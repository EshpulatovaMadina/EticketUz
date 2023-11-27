package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class CardController {
    private final CardService cardService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<CardResponseDTO> add(@RequestBody CardCreateDTO dto){
        return ResponseEntity.ok(cardService.add(dto));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/getCardsOfUser/{userId}")
    public ResponseEntity<List<CardResponseDTO>> getCardsOfUser(@PathVariable UUID userId){
        return ResponseEntity.ok(cardService.getCardsOfUser(userId));
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id){
        return ResponseEntity.ok(cardService.delete(id));
    }
}
