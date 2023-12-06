package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.eticket.DTO.request.TransactionCreateDto;
import uz.pdp.eticket.service.transaction.TransactionService;

import java.security.Principal;
import java.util.UUID;

/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    @Operation(
            description = "This method pays for the ticket from one card.",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping("/transaction")
    public ResponseEntity<String> transaction(@RequestBody TransactionCreateDto transactionCreateDto, Principal principal){
       return ResponseEntity.ok(transactionService.transaction(transactionCreateDto, UUID.fromString(principal.getName())));
    }
}
