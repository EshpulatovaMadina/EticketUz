package uz.pdp.eticket.DTO.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.eticket.entity.CardEntity;
import uz.pdp.eticket.entity.enums.CardType;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDTO {

    private UUID id;
    private Long number;
    private String expDate;
    private CardType cardType;
    private Double balance;

    public static CardResponseDTO toDTO(CardEntity entity) {
        return new CardResponseDTO(entity.getId(),entity.getNumber(),entity.getExpDate(), entity.getCardType(), entity.getBalance());
    }
}
