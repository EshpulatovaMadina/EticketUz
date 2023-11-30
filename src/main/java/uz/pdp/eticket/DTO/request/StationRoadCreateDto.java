package uz.pdp.eticket.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StationRoadCreateDto {
    private UUID stationId;
    private UUID roadId;
    private Integer orderName;

}
