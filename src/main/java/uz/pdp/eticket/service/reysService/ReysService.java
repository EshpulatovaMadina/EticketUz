package uz.pdp.eticket.service.reysService;
import org.springframework.scheduling.annotation.Scheduled;
import uz.pdp.eticket.DTO.request.ReysCreateDto;
import uz.pdp.eticket.DTO.response.ReysResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public interface ReysService {

    List<ReysResponseDto> getReysByLocation(String fromStation, String toStation, LocalDateTime fromDate, LocalDateTime toDate);

    ReysResponseDto create(ReysCreateDto dto);

    String deActive(UUID reysId);

    @Scheduled(fixedRate = 30000)
    void warnAllUsers();
}
