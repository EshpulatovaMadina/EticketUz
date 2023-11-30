package uz.pdp.eticket.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "bookings")
public class BookingsEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity userId;

    private String firstName;
    private String lastName;
    private String identity;
    private String birthday;

    @ManyToOne(cascade = CascadeType.ALL)
    private StationsEntity seatId;
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ReysEntity reys;

    @ManyToOne(cascade = CascadeType.ALL)
    private VagonEntity vagonId;

    private LocalDateTime date;

    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return "--Ticket--\n" +
               " user: " + userId.getName() + "\n" +
               " identity: '" + identity + "\n" +
               " seat: " + seatId.getSequenceNumber() + "\n" +
               " price: " + price + "\n" +
               " date: " + date.format(formatter);
    }
}
