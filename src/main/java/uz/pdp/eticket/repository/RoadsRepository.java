package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.RoadsEntity;

import java.util.Optional;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Repository
public interface RoadsRepository extends JpaRepository<RoadsEntity, UUID> {
    Optional<RoadsEntity> findAllByDirectionOrderByStations(String direction);
    Optional<RoadsEntity> findAllByDirection(String direction);
    Boolean existsByDirection(String direction);

    @Query("SELECT r.direction FROM roads r " +
            "JOIN r.stations fromStation " +
            "JOIN r.stations toStation " +
            "WHERE fromStation.name = :fromStationName " +
            "AND toStation.name = :toStationName " +
            "AND r.direction = :direction")
    String findAllDirectionByStations(@Param("fromStation") String fromStation,
                                      @Param("toStation") String toStation);
}
