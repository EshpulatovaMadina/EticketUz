package uz.pdp.eticket.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoadsResponseDto {
    private UUID id;
    private String direction;
    private List<StationResponseDto> stations;

    public RoadsResponseDto(UUID id, String direction) {
        this.id = id;
        this.direction = direction;
    }
}
