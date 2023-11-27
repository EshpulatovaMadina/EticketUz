package uz.pdp.eticket.DTO.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.enums.VagonType;

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
public class VagonResponseDto {
    private UUID vagonId;
    private String number;
    private UUID locomotiveId;
    private VagonType vagonTypes;
    private Integer numberOnTheTrain;
    protected LocalDateTime cratedDate;

    public VagonResponseDto(UUID id, String  number, VagonType vagonTypes, Integer numberOnTheTrain) {
        this.vagonId = id;
        this.number = number;
        this.vagonTypes = vagonTypes;
        this.numberOnTheTrain = numberOnTheTrain;

    }
}
