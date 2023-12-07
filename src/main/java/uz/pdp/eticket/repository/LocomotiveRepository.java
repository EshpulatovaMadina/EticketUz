package uz.pdp.eticket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.LocomotiveEntity;

import java.util.Optional;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@Repository
public interface LocomotiveRepository extends JpaRepository<LocomotiveEntity, UUID> {
    Page<LocomotiveEntity> findAllByIsActiveTrue(PageRequest pageRequest);
    Optional<LocomotiveEntity> findByName(String name);
}
