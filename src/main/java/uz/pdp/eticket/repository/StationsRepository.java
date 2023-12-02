package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.entity.StationEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface StationsRepository extends JpaRepository<StationEntity, UUID> {
    List<StationEntity> findAllByLocation(String location);
}
