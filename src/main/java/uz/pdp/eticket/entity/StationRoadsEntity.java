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
@Entity(name = "stationRoads")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"station_id","road_id","order_number"}))
public class StationRoadsEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private StationEntity station;

    @ManyToOne()
    @JoinColumn(name = "road_id", insertable = false, updatable = false)
    private RoadsEntity road;

    @Column(name = "order_number", insertable = false, updatable = false)
    private Integer orderNumber; //buuu stationnning roadda qaysi orinda turgani

}
