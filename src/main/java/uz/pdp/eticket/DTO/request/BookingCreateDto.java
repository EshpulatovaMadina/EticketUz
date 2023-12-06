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
public class BookingCreateDto {
    // userId princpalda kirib keladi
//    @NotNull(message = "")
//    private UUID userId;

    @NotBlank(message = "")
    private String identity;

    @NotNull(message = "")
    private UUID reysId;

    @NotNull(message = "")
    private UUID vagonId;

    private UUID seatId;
    private Double price;

    @NotNull(message = "")
    private LocalDateTime date;
}
