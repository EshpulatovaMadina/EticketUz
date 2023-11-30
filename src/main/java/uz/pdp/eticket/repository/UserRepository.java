package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAllByRoleAndIsActiveTrue(String role);
    Optional<UserEntity> findByIdAndIsActiveTrue(UUID userId);

    @Query(nativeQuery = false, value = """
        SELECT u
        FROM booking b
        INNER JOIN users u on u.id = b.user.id
        INNER JOIN reys r on r.id = b.reys.id
        WHERE r.startDate = :localDateTime
        """)
    List<UserEntity> findAllByStartDate(@Param("localDateTime") LocalDateTime localDateTime);

}
