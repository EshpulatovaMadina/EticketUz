package uz.pdp.eticket.service.stationRoadsService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.entity.StationRoadsEntity;
import uz.pdp.eticket.repository.StationRoadsRepository;

import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RequiredArgsConstructor
@Service
public class StationRoadsServiceImpl implements StationRoadsService {
    private final StationRoadsRepository stationRoadsRepository;

    public void save(UUID roadId, List<StationRoadCreateDto> stations) {
        for (StationRoadCreateDto station : stations) {
            stationRoadsRepository.save(new StationRoadsEntity(station.getStationId(), roadId));
        }
    }

    @Override
    public void update(UUID roadId, List<StationRoadCreateDto> stations) {
        List<StationRoadsEntity> all = stationRoadsRepository.findAllByRoadId(roadId);///oldin bor stationlar bu jjjj
        List<UUID> oldStations = all.stream().map(StationRoadsEntity::getStationId).toList(); // eski stationlar idlari
        List<UUID> newStations = stations.stream().map(StationRoadCreateDto::getStationId).toList();

        List<StationRoadsEntity> saveList = newStations.stream().filter(id -> {
            return !oldStations.contains(id); //eski stationlarda bolmasa
        }).map(newStationId -> {
            return new StationRoadsEntity(newStationId, roadId);
        }).toList(); // save qiladigan list
        List<StationRoadsEntity> deleteAll = all.stream().filter(item -> {
            return !newStations.contains(item.getStationId());
        }).toList();

        stationRoadsRepository.saveAll(saveList);

        stationRoadsRepository.deleteAll(deleteAll);
    }

    @Override
    public List<String> findAllDirectionByStations(String fromStation, String toStation) {
        return stationRoadsRepository.findAllDirectionByStation(fromStation, toStation);
    }


}
