package uz.pdp.eticket.DTO.request;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.enums.SeatType;

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
public class SeatsCreateDto {
    private Double price;
    @NotNull(message = "Max speed cannot be empty or blank")
    private Integer number;
    @NotNull(message = "Vagon id cannot be empty or blank")
    private UUID vagonId;
    @NotNull(message = "Seat type cannot be empty or blank")
    private SeatType location;
}
