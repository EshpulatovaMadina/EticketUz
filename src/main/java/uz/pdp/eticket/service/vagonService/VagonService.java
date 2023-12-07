package uz.pdp.eticket.service.vagonService;

import uz.pdp.eticket.DTO.request.VagonCreateDto;
import uz.pdp.eticket.DTO.response.FreeVagonResponseDto;
import uz.pdp.eticket.DTO.response.VagonResponseDto;
import uz.pdp.eticket.entity.VagonEntity;

import java.util.List;
import java.util.UUID;


public interface VagonService {
    List<VagonResponseDto> create(List<VagonCreateDto> dto, Double seatPrice);
    List<VagonResponseDto> setLocomotive(List<UUID> vagonsId, UUID locomotiveId);
    String disActive(UUID vagonId);
    VagonResponseDto isActive(UUID vagonId);
    VagonResponseDto getById(UUID vagonId);
    List<VagonResponseDto> getVagonsOfLocomotive(UUID locomotiveId);
    VagonEntity findById(UUID vagonId);
    VagonEntity parse(VagonCreateDto dto);
    VagonResponseDto parse(VagonEntity vagon);

    List<FreeVagonResponseDto> getFreeVagon(UUID locomotiveId, UUID reysId);


    List<VagonResponseDto> getAll(int page, int size);
}
