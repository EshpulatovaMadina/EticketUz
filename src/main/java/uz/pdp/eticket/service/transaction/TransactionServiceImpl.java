package uz.pdp.eticket.service.transaction;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.TransactionCreateDto;
import uz.pdp.eticket.entity.Transaction;
import uz.pdp.eticket.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private TransactionRepository transactionRepository;
    private ModelMapper modelMapper;
    @Override
    public String transaction(TransactionCreateDto transaction) {
        Transaction save = transactionRepository.save(modelMapper.map(transaction, Transaction.class));
        return "Successfully transaction";
    }
}
