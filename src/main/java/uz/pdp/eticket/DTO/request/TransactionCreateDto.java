package uz.pdp.eticket.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionCreateDto {
    private UUID cardId;
    private Double amount;
//    private UUID userId;
    private UUID bookingId;
}
