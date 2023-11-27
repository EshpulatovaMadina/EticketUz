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
@Setter
@Getter
public class LocomotiveResponseDto {
    private UUID locomotiveId;
    private String name;
    private Integer maxSpeed;
    private Integer maxVagons;
    protected LocalDateTime cratedDate;
}
