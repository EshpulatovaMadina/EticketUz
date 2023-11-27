package uz.pdp.eticket.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
public class StationsResponseDto {
    private UUID id;
    private String name;
    private String location;
    private RoadsResponseDto roadsResponseDto;
    private StationsResponseDto nextStation;
    private StationsResponseDto prevStation;
    private LocalDateTime cratedDate;
}
