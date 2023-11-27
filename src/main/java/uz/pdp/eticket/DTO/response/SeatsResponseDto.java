package uz.pdp.eticket.DTO.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.enums.SeatType;

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
public class SeatsResponseDto {
    private UUID seatsId;
    private Integer number;
    private UUID vagonId;
    private Double price;
    private SeatType location;
    private LocalDateTime cratedDate;
    private Boolean isActive;
}
