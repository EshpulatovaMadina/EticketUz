package uz.pdp.eticket.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FreeVagonResponseDto {
    private Integer vagonNumber;
    private Integer countFreeSeat;
    private Double price;
    private List<SeatsResponseDto> freeSeats;

}
