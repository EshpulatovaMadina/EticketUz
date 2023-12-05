package uz.pdp.eticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "station_roads")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"station_id","road_id","order_number"}))
public class StationRoadsEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @ManyToOne()
    @JoinColumn(name = "road_id")
    private RoadsEntity road;

    @Column(name = "order_number")
    private Integer orderNumber; //buuu stationnning roadda qaysi orinda turgani

}
