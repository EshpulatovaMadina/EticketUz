package uz.pdp.eticket.DTO.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.entity.ReysEntity;

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
public class BookingsResponseDto {
    private UUID bookingId;
    private UUID userId;
    private String identity;
    private UUID  reysId;
    private Integer vagonNumber;
    private LocalDateTime startDate;
    private LocalDateTime createdDate;
}
