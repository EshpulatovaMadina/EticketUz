package uz.pdp.eticket.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.Transaction;
import java.util.UUID;
@Repository
public  interface TransactionRepository extends JpaRepository<Transaction, UUID> {


}
