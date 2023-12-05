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
public class StationRoadsResponseDto {
    private UUID id;
    private UUID stationId;
    private String stationName;
    private UUID roadId;
    private String roadName;
    private Integer orderNumber;
    private LocalDateTime createdDate;
}
