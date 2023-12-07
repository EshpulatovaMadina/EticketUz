package uz.pdp.eticket.service.reysService;
import uz.pdp.eticket.DTO.request.ReysCreateDto;
import uz.pdp.eticket.DTO.response.ReysResponseDto;
import uz.pdp.eticket.entity.ReysEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReysService {
    List<ReysResponseDto> getReysByLocation(String fromStation, String toStation, LocalDateTime fromDate, LocalDateTime toDate);

    ReysResponseDto create(ReysCreateDto dto);

    String disActive(UUID reysId);

    ReysEntity findById(UUID reysId);

    List<ReysResponseDto> getAll(int page, int size);

}
