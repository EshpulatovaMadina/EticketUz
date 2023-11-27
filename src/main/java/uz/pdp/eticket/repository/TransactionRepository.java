package uz.pdp.eticket.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.eticket.entity.Transaction;
import java.util.UUID;

public  interface TransactionRepository extends JpaRepository<Transaction, UUID> {


}
