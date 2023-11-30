package uz.pdp.eticket.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.eticket.entity.enums.CardType;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardCreateDTO {
    private Long number;
    private String expDate; // 09/25
    private CardType cardType;
//    private UUID userId;
}
