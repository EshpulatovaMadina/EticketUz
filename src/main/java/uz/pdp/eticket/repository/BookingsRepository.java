package uz.pdp.eticket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.eticket.entity.BookingEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingsRepository extends JpaRepository<BookingEntity, UUID> {
    Boolean existsAllByReysId(UUID reys_id);
    Boolean existsBySeatIdAndReysId(UUID seatId, UUID reys_id);
    List<BookingEntity> findAllByUserId(UUID userId);

    @Modifying
    @Query("UPDATE booking b SET b.isActive = false WHERE b.createdDate < :createdDate")
    void updateBookingStatusByCreatedDateBefore(LocalDateTime createdDate);

}
