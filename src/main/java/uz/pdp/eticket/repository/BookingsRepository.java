package uz.pdp.eticket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.eticket.entity.BookingEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingsRepository extends JpaRepository<BookingEntity, UUID> {
    Boolean existsAllByReysId(UUID reys_id);
    Boolean existsBySeatIdAndReysId(UUID seatId, UUID reys_id);
    List<BookingEntity> findAllByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM booking b WHERE b.createdDate < :createdDate")
    void deleteBookingEntitiesByCreatedDateBefore(LocalDateTime createdDate);
}
