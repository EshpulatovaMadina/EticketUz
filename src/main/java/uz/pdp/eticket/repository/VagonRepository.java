package uz.pdp.eticket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.VagonEntity;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Repository
public interface VagonRepository extends JpaRepository<VagonEntity, UUID> {
    Boolean findAllByLocomotiveIdAndNumber(UUID locomotive_id, String number);
    Boolean existsByNumber(String number);
    Page<VagonEntity> findAllByIsActiveTrue(PageRequest pageRequest);
    Integer countAllByLocomotiveId( UUID locomotive_id);
    List<VagonEntity> findAllByLocomotiveIdOrderByNumberOnTheTrain(UUID locomotive_id);
    List<VagonEntity> findAllByLocomotiveId(UUID locomotive_id);
}
