package uz.pdp.eticket.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.enums.VagonType;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "vagon")
public class VagonEntity extends BaseEntity {

    @Column(unique = true)
    private String number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocomotiveEntity locomotive;

    @Enumerated(EnumType.STRING)
    private VagonType vagonTypes;

    private Integer numberOnTheTrain;

    public VagonEntity(String number, LocomotiveEntity locomotive, VagonType vagonTypes) {
        this.number = number;
        this.locomotive = locomotive;
        this.vagonTypes = vagonTypes;
    }

    public VagonEntity(String number, VagonType vagonTypes) {
        this.number = number;
        this.vagonTypes = vagonTypes;
    }

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<SeatsResponseDto> seats;
}
