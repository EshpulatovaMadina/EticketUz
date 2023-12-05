package uz.pdp.eticket.DTO.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.enums.VagonType;

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
public class VagonCreateDto {
    @NotBlank(message = "Number cannot be empty or blank")
    private String number;

    private UUID locomotiveId;
    private Integer numberOnTheTrain;

    @NotNull(message = "Vagon type cannot be empty or blank")
    private String vagonTypes;

}
