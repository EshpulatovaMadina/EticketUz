package uz.pdp.eticket.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReysCreateDto {
    @NotNull(message = "")
    private UUID roadsId;

    @NotBlank(message = "")
    private String direction;

    @NotNull(message = "")
    private UUID fromStation;

    @NotNull(message = "")
    private UUID toStation;

    @NotNull(message = "")
    private UUID locomotiveId;
    ///documentatsiyada formatni aytish kerak     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "")
    private LocalDateTime startDate; //yurish vaqt
}
