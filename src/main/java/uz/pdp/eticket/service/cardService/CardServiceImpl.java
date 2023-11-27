package uz.pdp.eticket.service.cardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.CardCreateDTO;
import uz.pdp.eticket.DTO.response.CardResponseDTO;
import uz.pdp.eticket.entity.CardEntity;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.CardRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl  implements CardService{
    private  CardRepository cardRepository;
    public CardResponseDTO add(CardCreateDTO dto) {
        CardEntity entity = new CardEntity();
        entity.setNumber(dto.getNumber());
        entity.setExpDate(dto.getExpDate());
         entity.setUserId(dto.getUserId());
        cardRepository.save(entity);
        return CardResponseDTO.toDTO(entity);
    }
    public List<CardResponseDTO> getCardsOfUser(UUID userId) {
        return cardRepository
                .findAllByUserId(userId)
                .stream()
                .map(CardResponseDTO::toDTO)
                .toList();
    }
    public Boolean delete(UUID id) {
        CardEntity card = cardRepository.findById(id).orElseThrow(() -> new DataNotFoundException("This card not found"));
        card.setIsActive(false);
        cardRepository.save(card);
        return true;
    }
}
