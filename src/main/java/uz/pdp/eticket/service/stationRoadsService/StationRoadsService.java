package uz.pdp.eticket.service.stationRoadsService;

import org.springframework.data.repository.query.Param;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.response.StationRoadsResponseDto;
import uz.pdp.eticket.entity.StationRoadsEntity;

import java.util.List;
import java.util.UUID;


public interface StationRoadsService {
    void save(UUID roadId, List<StationRoadCreateDto> stations);
    void update(UUID roadId, List<StationRoadCreateDto> stations);
    List<String> findAllDirectionByStations( String fromStation, String toStation);
    List<StationRoadsResponseDto> getStationOfRoad(UUID roadId);

    Integer countAllByRoadId(UUID roadId);

}
