package uz.pdp.eticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.DTO.request.RoadsCreateDto;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "stationRoads")
public class StationRoadsEntity extends BaseEntity {

    @Column(name = "station_id")
    private UUID stationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_Id", insertable = false, updatable = false)
    private StationsEntity station;


    @Column(name = "road_id")
    private UUID roadId;

    @ManyToOne()
    @JoinColumn(name = "road_id", insertable = false, updatable = false)
    private RoadsEntity road;

    private Integer orderNumber; //buuu jstationnning roadda qaysi orinda turgani
    public StationRoadsEntity(UUID stationId, UUID roadId) {
        this.roadId = roadId;
        this.stationId = stationId;
        this.orderNumber = getOrderNumber();
    }
}
