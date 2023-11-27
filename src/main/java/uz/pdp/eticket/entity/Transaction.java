package uz.pdp.eticket.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "transaction")
public class Transaction extends BaseEntity{
    private UUID cardId;
    private Double amount;
    private UUID userId;
    private UUID bookingId;
}
