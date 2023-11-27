package uz.pdp.eticket.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.eticket.entity.CardEntity;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    List<CardEntity> findAllByUserId(UUID userId);
}