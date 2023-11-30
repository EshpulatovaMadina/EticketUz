package uz.pdp.eticket.service.cardService;

import uz.pdp.eticket.DTO.request.CardCreateDTO;
import uz.pdp.eticket.DTO.response.CardResponseDTO;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface CardService {
    CardResponseDTO add(CardCreateDTO dto, UUID userId);
    List<CardResponseDTO> getCardsOfUser(UUID userId);
    Boolean delete(UUID id);
    CardResponseDTO getById(UUID cardId);
    String withdrawMoney(UUID cardId, Double amount);
}
