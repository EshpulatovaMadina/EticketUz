package uz.pdp.eticket.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StationResponseDto {
    private UUID id;
    private String name;
    private String location;
    private RoadsResponseDto roadsResponseDto;
    private StationResponseDto nextStation;
    private StationResponseDto prevStation;
    private LocalDateTime cratedDate;
    private Boolean isActive;
}
