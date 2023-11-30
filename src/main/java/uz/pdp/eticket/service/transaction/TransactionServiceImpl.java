package uz.pdp.eticket.service.transaction;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.TransactionCreateDto;
import uz.pdp.eticket.DTO.response.CardResponseDTO;
import uz.pdp.eticket.entity.BookingEntity;
import uz.pdp.eticket.entity.Transaction;
import uz.pdp.eticket.exception.BadRequestException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.BookingsRepository;
import uz.pdp.eticket.repository.TransactionRepository;
import uz.pdp.eticket.service.cardService.CardService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final BookingsRepository bookingsRepository;
    private final CardService cardService;

    @Override
    public String transaction(TransactionCreateDto transaction, UUID userId) {
        Optional<BookingEntity> byId = bookingsRepository.findById(transaction.getBookingId());
        if (byId.isEmpty()){
            throw new DataNotFoundException("Because you have not paid within 10 minutes, the booking has been canceled for you.");
        }
        CardResponseDTO byId1 = cardService.getById(transaction.getCardId());
        if (byId1.getBalance() >= transaction.getAmount() * 0.1 +transaction.getAmount()){
            String s = cardService.withdrawMoney(byId1.getId(), transaction.getAmount());
            Transaction map = modelMapper.map(transaction, Transaction.class);
            map.setUserId(userId);
            transactionRepository.save(map);
            return "Successfully transaction";
        }
            throw  new BadRequestException("There are insufficient funds on your card.");
    }
}
