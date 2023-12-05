package uz.pdp.eticket.service.stationRoadsService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.entity.RoadsEntity;
import uz.pdp.eticket.entity.StationEntity;
import uz.pdp.eticket.entity.StationRoadsEntity;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.RoadsRepository;
import uz.pdp.eticket.repository.StationRoadsRepository;
import uz.pdp.eticket.repository.StationsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StationRoadsServiceImpl implements StationRoadsService {
    private final StationRoadsRepository stationRoadsRepository;
    private final RoadsRepository roadsRepository;
    private final StationsRepository stationsRepository;

    public void save(UUID roadId, List<StationRoadCreateDto> stations) {

        RoadsEntity roadsEntity = roadsRepository.findById(roadId)
            .orElseThrow(() -> new DataNotFoundException("Road not found with id: " + roadId));

        for (StationRoadCreateDto station : stations) {

            StationEntity stationEntity = stationsRepository.findById(station.getStationId())
                    .orElseThrow(() -> new DataNotFoundException("Station not found with id: " + station.getStationId()));

            Optional<StationRoadsEntity> stationRoadsEntity = stationRoadsRepository.findByRoadIdAndStationIdAndOrderNumber(roadId, station.getStationId(), station.getOrderNumber());

            if(stationRoadsEntity.isPresent()) {
                throw new DataAlreadyExistsException("This station already exists on this road");
            }

            StationRoadsEntity entity = new StationRoadsEntity(stationEntity, roadsEntity, station.getOrderNumber());
            stationRoadsRepository.save(entity);
        }
    }

    @Override
    public void update(UUID roadId, List<StationRoadCreateDto> stations) {
        List<StationRoadsEntity> all = stationRoadsRepository.findAllByRoadId(roadId);///oldin bor stationlar bu jjjj
        List<UUID> oldStations = all.stream().map(item -> item.getStation().getId()).toList(); // eski stationlar idlari
        List<UUID> newStations = stations.stream().map(StationRoadCreateDto::getStationId).toList();

        List<StationRoadsEntity> saveList = newStations.stream().filter(id -> {
            return !oldStations.contains(id); //eski stationlarda bolmasa
        }).map(newStationId -> {
            StationEntity stationEntity = stationsRepository.findById(newStationId)
                    .orElseThrow(() -> new DataNotFoundException("Station not found with id: " + newStationId));
            RoadsEntity roadsEntity = roadsRepository.findById(roadId)
                    .orElseThrow(() -> new DataNotFoundException("Road not found with id: " + roadId));
            // bu yerda order numberni qayerdan oliwni bilmadim, o'wancun uni o'rniga null qo'ydim
            return new StationRoadsEntity(stationEntity, roadsEntity,null);
        }).toList(); // save qiladigan list
        List<StationRoadsEntity> deleteAll = all.stream().filter(item -> {
            return !newStations.contains(item.getStation().getId());
        }).toList();

        stationRoadsRepository.saveAll(saveList);

        stationRoadsRepository.deleteAll(deleteAll);
    }

    @Override
    public List<String> findAllDirectionByStations(String fromStation, String toStation) {
        return stationRoadsRepository.findAllDirectionByStation(fromStation, toStation);
    }


}
