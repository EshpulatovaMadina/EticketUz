package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.SeatsEntity;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Repository
public interface SeatsRepository extends JpaRepository<SeatsEntity, UUID> {
    Boolean existsByVagonIdAndNumber(UUID vagon_id, Integer number);
    List<SeatsEntity> findAllByVagonId(UUID vagon_id);
}
