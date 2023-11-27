package uz.pdp.eticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "station")
public class StationsEntity extends BaseEntity {
    private String name;
    private String location;
     @ManyToMany(cascade = CascadeType.ALL)
    private List<RoadsEntity> roadsEntity;
    private Integer sequenceNumber;
    // shuyerda nimadur bor edi
    @OneToOne(cascade = CascadeType.ALL)
    private StationsEntity nextStation;
    @OneToOne(cascade = CascadeType.ALL)
    private StationsEntity prevStation;
}



