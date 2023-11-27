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
    @Query("SELECT r.road.direction FROM stationRoads r " +
            "JOIN r.station fromStation " +
            "JOIN r.station toStation " +
            "WHERE fromStation.name = :fromStationName " +
            "AND toStation.name = :toStationName " +
            "AND r.road.direction = :direction")
    String findAllDirectionByStations(@Param("fromStation") String fromStation,
                                      @Param("toStation") String toStation);

}
