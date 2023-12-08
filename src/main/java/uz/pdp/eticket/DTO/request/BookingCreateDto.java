package uz.pdp.eticket.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingCreateDto {
    // userId princpalda kirib keladi
    @NotBlank(message = "")
    private String firstName;

    @NotBlank(message = "")
    private String lastName;

    @NotBlank(message = "")
    private String passportNumberAndSeries;

    private UUID seatId;

    private LocalDate birthday;

    @NotNull(message = "")
    private UUID reysId;

    @NotNull(message = "")
    private UUID vagonId;

}
