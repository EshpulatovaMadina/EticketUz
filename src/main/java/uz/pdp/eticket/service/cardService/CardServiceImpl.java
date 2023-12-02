package uz.pdp.eticket.service.cardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;
    public CardResponseDTO add(CardCreateDTO dto, UUID userId) {
        CardEntity entity = new CardEntity();
        entity.setNumber(dto.getNumber());
        entity.setExpDate(dto.getExpDate());
         entity.setUserId(userId);
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

    @Override
    public CardResponseDTO getById(UUID cardId) {
        CardEntity card = cardRepository.findById(cardId).orElseThrow(() -> new DataNotFoundException("This card not found"));
        return  modelMapper.map(card, CardResponseDTO.class);
    }

    @Override
    public String withdrawMoney(UUID cardId, Double amount) {
        CardEntity card = cardRepository.findById(cardId).orElseThrow(() -> new DataNotFoundException("This card not found"));
        card.setBalance(card.getBalance() - amount);
        cardRepository.save(card);
        return "Successfully";
    }
}
