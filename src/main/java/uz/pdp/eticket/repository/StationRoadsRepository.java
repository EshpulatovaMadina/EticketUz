package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.StationRoadsEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface StationRoadsRepository extends JpaRepository<StationRoadsEntity, UUID> {
    List<StationRoadsEntity> findAllByRoadId(UUID roadId);
    List<StationRoadsEntity> findAllByRoadIdOrderByOrderNumber(UUID roadId);
    Integer countAllByRoadId(UUID roadId);
    Optional<StationRoadsEntity> findAllByStationId(UUID station_id);
    @Query("SELECT r.road.direction FROM station_roads r  WHERE r.station.name = :fromStation " +
            "  AND r.orderNumber < (SELECT r2.orderNumber FROM station_roads r2 WHERE r2.station.name = :toStation AND r2.road = r.road)")
    List<String> findAllDirectionByStation(@Param("fromStation") String fromStation, @Param("toStation") String toStation);

    Optional<StationRoadsEntity> findByRoadIdAndStationIdAndOrderNumber(UUID roadId,UUID stationId,Integer orderNumber);

}
