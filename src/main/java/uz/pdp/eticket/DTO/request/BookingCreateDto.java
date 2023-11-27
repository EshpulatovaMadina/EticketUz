package uz.pdp.eticket.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Getter
@Setter
public class BookingCreateDto {
    @NotNull(message = "")
    private UUID userId;

    @NotBlank(message = "")
    private String firstName;

    @NotBlank(message = "")
    private String lastName;

    @NotBlank(message = "")
    private String identity;

    @NotBlank(message = "")
    private String birthday;

    @NotNull(message = "")
    private UUID reysId;

    @NotNull(message = "")
    private UUID vagonId;

    private UUID seatId;
    private Double price;

    @NotNull(message = "")
    private LocalDateTime date;
}
