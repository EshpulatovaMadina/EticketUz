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
    Optional<RoadsEntity> findAllByDirection(String direction);
    Boolean existsByDirection(String direction);

  }
