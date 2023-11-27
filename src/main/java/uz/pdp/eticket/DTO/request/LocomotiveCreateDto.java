package uz.pdp.eticket.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LocomotiveCreateDto {
    @NotBlank(message = "Locomotive name cannot be empty or blank")
    private String name;
    @NotNull(message = "Max speed cannot be empty or blank")
    private Integer maxSpeed;
    @NotNull(message = "Max speed cannot be empty or blank")
    private Integer maxVagons;
}
