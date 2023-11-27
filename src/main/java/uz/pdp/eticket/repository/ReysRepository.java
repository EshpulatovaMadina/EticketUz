package uz.pdp.eticket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.eticket.entity.ReysEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface ReysRepository extends JpaRepository<ReysEntity, UUID> {

     List<ReysEntity> findAllByDirectionAndFromDate(String direction, LocalDateTime fromDate);
}
