package uz.pdp.eticket.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.enums.Direction;

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
public class ReysResponseDto {
    private UUID reysId;
    private UUID roadsId;
    ///direction ne kk
    private Direction direction;
    private UUID fromStation;
    private UUID toStation;
    private UUID locomotiveId;
    ///documentatsiyada formatni aytish kerak     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate; ///poyezd qachon  jo'nashi'
    private LocalDateTime createDate;





}
