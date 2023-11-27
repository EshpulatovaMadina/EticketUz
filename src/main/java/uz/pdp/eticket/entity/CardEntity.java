package uz.pdp.eticket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import uz.pdp.eticket.entity.enums.CardType;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Entity
@Getter
@Setter
@Table(name = "card")
public class CardEntity extends BaseEntity{
    private Long number;
    private String expDate; // 09/25
    private CardType cardType;
    private Double balance;
    private UUID userId;
}
