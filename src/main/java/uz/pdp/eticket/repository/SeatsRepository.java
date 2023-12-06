package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.SeatEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatsRepository extends JpaRepository<SeatEntity, UUID> {
    Boolean existsByVagonIdAndNumber(UUID vagonId, Integer number);
    List<SeatEntity> findAllByVagonId(UUID vagonId);
}
