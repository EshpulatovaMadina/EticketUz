package uz.pdp.eticket.service.transaction;

import uz.pdp.eticket.DTO.request.TransactionCreateDto;

import java.util.UUID;

public interface TransactionService {
    String transaction(TransactionCreateDto transaction, UUID userId);

}
