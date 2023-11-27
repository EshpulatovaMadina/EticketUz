package uz.pdp.eticket.service.stationRoadsService;

import org.springframework.data.repository.query.Param;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface StationRoadsService {
    void save(UUID roadId, List<StationRoadCreateDto> stations);
    void update(UUID roadId, List<StationRoadCreateDto> stations);
    List<String> findAllDirectionByStations( String fromStation, String toStation);
}
