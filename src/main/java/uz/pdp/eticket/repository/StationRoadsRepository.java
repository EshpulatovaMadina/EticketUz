package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.eticket.entity.StationRoadsEntity;
import uz.pdp.eticket.entity.StationsEntity;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface StationRoadsRepository extends JpaRepository<StationRoadsEntity, UUID> {
    List<StationRoadsEntity> findAllByRoadId(UUID roadId);
    List<StationRoadsEntity> findAllByRoadIdOrderByOrderNumber(UUID roadId);

    @Query("SELECT r.road.direction FROM stationRoads r  WHERE r.station.name = :fromStation " +
            "  AND r.orderNumber < (SELECT r2.orderNumber FROM stationRoads r2 WHERE r2.station.name = :toStation AND r2.road = r.road)")
    List<String> findAllDirectionByStation(@Param("fromStation") String fromStation, @Param("toStation") String toStation);

}
