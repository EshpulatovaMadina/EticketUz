package uz.pdp.eticket.service.transaction;

import uz.pdp.eticket.DTO.request.TransactionCreateDto;

public interface TransactionService {
    String transaction(TransactionCreateDto transaction);

}
