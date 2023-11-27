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
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private TransactionService transactionService;
    @Operation(
            description = "This method pays for the ticket from one card.",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping("/transaction")
    public ResponseEntity<String> transaction(@RequestBody TransactionCreateDto transactionCreateDto){
       return ResponseEntity.ok(transactionService.transaction(transactionCreateDto));
    }
}
