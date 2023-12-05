package uz.pdp.eticket.DTO.request;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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

}
