package uz.pdp.eticket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.eticket.entity.BookingsEntity;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface BookingsRepository extends JpaRepository<BookingsEntity, UUID> {
    Boolean existsAllByReysId(UUID reys_id);
    Boolean existsBySeatIdAndReysId(UUID seatId, UUID reys_id);
    List<BookingsEntity> findAllByUserId(UUID userId);
}
