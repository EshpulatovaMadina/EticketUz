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

/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "booking")
public class BookingEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity user;

    private String firstName;
    private String lastName;
    private String identity;
    private String birthday;

    @ManyToOne(cascade = CascadeType.ALL)
    private SeatEntity seat;
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ReysEntity reys;

    @ManyToOne(cascade = CascadeType.ALL)
    private VagonEntity vagon;

    private LocalDateTime date;

    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return "--Ticket--\n" +
               " user: " + user.getName() + "\n" +
               " identity: '" + identity + "\n" +
               " seat: " + seat.getNumber() + "\n" +
               " price: " + price + "\n" +
               " date: " + date.format(formatter);
    }
}
