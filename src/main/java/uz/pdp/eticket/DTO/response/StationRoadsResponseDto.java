package uz.pdp.eticket.DTO.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.RoadsEntity;
import uz.pdp.eticket.entity.StationsEntity;

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
}
