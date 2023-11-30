package uz.pdp.eticket.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.StationRoadCreateDto;
import uz.pdp.eticket.DTO.response.StationRoadsResponseDto;
import uz.pdp.eticket.service.stationRoadsService.StationRoadsService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class StationRoadController {
    private final StationRoadsService stationRoadsService;
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody List<StationRoadCreateDto> list, @RequestParam UUID roadId){
        stationRoadsService.save(roadId,list);
         return ResponseEntity.ok("Successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody List<StationRoadCreateDto> list, @RequestParam UUID roadId){
        stationRoadsService.update(roadId, list);
        return ResponseEntity.ok("Successfully");
    }

    @GetMapping("/get-direction-by-stations")
    public ResponseEntity<List<String>> findAllDirectionByStations(@RequestParam String fromStation,@RequestParam String toStation){ /// directionlar listi
         return ResponseEntity.ok(stationRoadsService.findAllDirectionByStations(fromStation, toStation));
    }

}
