package uz.pdp.eticket.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.eticket.entity.ReysEntity;
import uz.pdp.eticket.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReysRepository extends JpaRepository<ReysEntity, UUID> {

     List<ReysEntity> findAllByDirectionAndStartDate(String direction, LocalDateTime fromDate);
     List<ReysEntity> findAllByDirection(String direction);

    Page<ReysEntity> findAllByIsActiveTrue(PageRequest pageRequest);
}
