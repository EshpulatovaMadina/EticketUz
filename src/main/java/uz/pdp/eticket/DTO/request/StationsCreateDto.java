package uz.pdp.eticket.DTO.request;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.StationsEntity;

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
public class StationsCreateDto {
    @NotBlank(message = "")
    private String name;
    @NotBlank(message = "")
    private String location;

    private UUID roadId;

    private Integer number;

//    private UUID nextStation;
//    private StationsEntity prevStation;// hammasi kere yaxshi qiz smile🙁🙁

}
