package uz.pdp.eticket.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.enums.SeatType;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "seats")
public class SeatEntity extends BaseEntity {
    private Double price;
    private Integer number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private VagonEntity vagonId;

    @Enumerated(EnumType.STRING)
    private SeatType location;

}
