package uz.pdp.eticket.service.stationRoadsService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.response.StationRoadsResponseDto;
import uz.pdp.eticket.entity.RoadsEntity;
import uz.pdp.eticket.entity.StationEntity;
import uz.pdp.eticket.entity.StationRoadsEntity;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.RoadsRepository;
import uz.pdp.eticket.repository.StationRoadsRepository;
import uz.pdp.eticket.repository.StationsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StationRoadsServiceImpl implements StationRoadsService {
    private final StationRoadsRepository stationRoadsRepository;
    private final RoadsRepository roadsRepository;
    private final StationsRepository stationsRepository;

    @Transactional
    public void save(UUID roadId, List<StationRoadCreateDto> stations) {
        StationRoadsEntity save = null;
        RoadsEntity roadsEntity = roadsRepository.findById(roadId)
                .orElseThrow(() -> new DataNotFoundException("Road not found with id: " + roadId));
        for (StationRoadCreateDto stationCreate : stations) {
            StationEntity station = stationsRepository.findById(stationCreate.getStationId())
                    .orElseThrow(() -> new DataNotFoundException("Station not found with id: " + stationCreate.getStationId()));
            save = stationRoadsRepository.save(new StationRoadsEntity(station, roadsEntity, stationCreate.getOrderNumber()));
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

    @Override
    public List<StationRoadsResponseDto> getStationOfRoad(UUID roadId) {
        List<StationRoadsEntity> all = stationRoadsRepository.findAllByRoadId(roadId);///oldin bor stationlar bu jjjj
        return parse(all);
    }

    @Override
    public Integer countAllByRoadId(UUID roadId) {
        return stationRoadsRepository.countAllByRoadId(roadId);
    }

    private List<StationRoadsResponseDto> parse(List<StationRoadsEntity> stations){
        List<StationRoadsResponseDto> list = new ArrayList<>();
        for (StationRoadsEntity station : stations) {
            list.add(new StationRoadsResponseDto(station.getId(), station.getStation().getId(),station.getStation().getName(), station.getRoad().getId(), station.getRoad().getDirection(), station.getOrderNumber(), station.getCreatedDate()));
        }
        return list;
    }


}
