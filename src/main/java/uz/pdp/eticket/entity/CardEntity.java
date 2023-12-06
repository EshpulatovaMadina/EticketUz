package uz.pdp.eticket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import uz.pdp.eticket.entity.enums.CardType;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "card")
public class CardEntity extends BaseEntity {
    private Long number;
    private String expDate; // 09/25
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private Double balance = 1000_000.0;
    private UUID userId;
}
