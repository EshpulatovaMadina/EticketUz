package uz.pdp.eticket.service.vagonService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.VagonCreateDto;
import uz.pdp.eticket.DTO.response.FreeVagonResponseDto;
import uz.pdp.eticket.DTO.response.SeatsResponseDto;
import uz.pdp.eticket.DTO.response.VagonResponseDto;
import uz.pdp.eticket.entity.LocomotiveEntity;
import uz.pdp.eticket.entity.VagonEntity;
import uz.pdp.eticket.entity.enums.VagonType;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.exception.ExceededLimitException;
import uz.pdp.eticket.repository.VagonRepository;
import uz.pdp.eticket.service.bookingService.BookingsService;
import uz.pdp.eticket.service.locomotiveService.LocomotiveService;
import uz.pdp.eticket.service.seatsService.SeatService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@RequiredArgsConstructor
@Service
public class VagonServiceImpl implements VagonService{
    private final LocomotiveService locomotiveService;
    private final VagonRepository vagonRepository;
    private final ModelMapper modelMapper;
    private final SeatService seatService;
    private final BookingsService bookingsService;
    @Override
    public List<VagonResponseDto> create(List<VagonCreateDto> dtos, Double seatPrice) {
        List<VagonEntity> save = new ArrayList<>();
        for (VagonCreateDto dto : dtos) {
            if (vagonRepository.existsByNumber(dto.getNumber())) {
                throw new DataAlreadyExistsException("Such a digital wagon already exists ...  Please give another number for this vagon.");
            }
            VagonEntity vagon = parse(dto);
            save.add(vagonRepository.save(vagon));
            seatService.create(vagon.getId(), seatPrice);
        }
        return save.stream().map(this::parse).toList();
    }

    @Override
    public List<VagonResponseDto> setLocomotive(List<UUID> vagonsId, UUID locomotiveId) {
        LocomotiveEntity locomotive = locomotiveService.findById(locomotiveId);
        Integer counted = vagonRepository.countAllByLocomotiveId(locomotiveId);
        if (locomotive.getMaxVagons() <= counted){
            throw new ExceededLimitException("This locomotive is designed for only "+ locomotive.getMaxVagons()+" vagons. You cannot add another carriage.");
        }
        List<VagonEntity> list = new ArrayList<>();
        int i = 1;
        for (UUID vagonId : vagonsId) {
            VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
            LocomotiveEntity oldLocomotive = vagon.getLocomotive();
            Integer oldNumberOnTheTrain = vagon.getNumberOnTheTrain();
            vagon.setNumberOnTheTrain(counted + i++);
            vagon.setLocomotive(locomotive);
            vagonRepository.save(vagon);

            if (oldLocomotive != null){
                List<VagonEntity> all = vagonRepository.findAllByLocomotiveIdOrderByNumberOnTheTrain(oldLocomotive.getId());
                updateVagonNumberWhenLocomotiveChange(all, oldNumberOnTheTrain);
            }
            list.add(vagon);
        }
        return parse(list);
    }

    private void updateVagonNumberWhenLocomotiveChange(List<VagonEntity> all, Integer numberOnTheTrain) {
        List<VagonEntity> updated = new ArrayList<>();
        for (VagonEntity vagonEntity : all) {
            if(vagonEntity.getNumberOnTheTrain() >= numberOnTheTrain){
                vagonEntity.setNumberOnTheTrain(vagonEntity.getNumberOnTheTrain() - 1);
                updated.add(vagonEntity);
            }
        }
        vagonRepository.saveAll(updated);
    }
    private void updateVagonNumberOnTheTrain(List<VagonEntity> all, Integer numberOnTheTrain) {
        for (VagonEntity vagonEntity : all) {
            if(vagonEntity.getNumberOnTheTrain() >= numberOnTheTrain){
                vagonEntity.setNumberOnTheTrain(vagonEntity.getNumberOnTheTrain() + 1);
            }
        }
        vagonRepository.saveAll(all);
    }


    @Override
    public String disActive(UUID vagonId) {
        VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
        vagon.setIsActive(false);
        vagonRepository.save(vagon);
        return "Successfully";
    }


    @Override
    public VagonResponseDto isActive(UUID vagonId) {
        VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
            vagon.setIsActive(true);
            vagonRepository.save(vagon);
        return parse(vagon);
    }


    @Override
    public VagonResponseDto getById(UUID vagonId) {
        VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
        return parse(vagon);
    }

    @Override
    public List<VagonResponseDto> getVagonsOfLocomotive(UUID locomotiveId) {
        List<VagonEntity> vagons = vagonRepository.findAllByLocomotiveIdOrderByNumberOnTheTrain(locomotiveId);
        return parse(vagons);
    }

    @Override
    public VagonEntity findById(UUID vagonId) {
        return vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
    }

    @Override
    public VagonEntity parse(VagonCreateDto dto) {
        if (dto.getLocomotiveId() == null){
            return new VagonEntity(dto.getNumber(),null,  VagonType.valueOf(dto.getVagonTypes()), null);
        }else {
            LocomotiveEntity byId = locomotiveService.findById(dto.getLocomotiveId());
            List<VagonEntity> all = vagonRepository.findAllByLocomotiveId(byId.getId());
            updateVagonNumberOnTheTrain(all, dto.getNumberOnTheTrain());
            return new VagonEntity(dto.getNumber(), byId, VagonType.valueOf(dto.getVagonTypes()), dto.getNumberOnTheTrain());
        }
    }



    private List<VagonResponseDto> parse(List<VagonEntity> entities){
        List<VagonResponseDto> list = new ArrayList<>();
        for (VagonEntity vagon : entities) {
            VagonResponseDto map = modelMapper.map(vagon, VagonResponseDto.class);
            map.setVagonId(vagon.getId());
            map.setCratedDate(vagon.getCreatedDate()); list.add(map);
        }
        return list;
    }

    @Override
    public VagonResponseDto parse(VagonEntity vagon) {
        VagonResponseDto map = modelMapper.map(vagon, VagonResponseDto.class);
        map.setLocomotiveId(vagon.getLocomotive().getId());
        return map;
    }

    /***
     * bu method vagon tanlash jarayonidagi. bunda hamma orindiqlarni qaytaradi va frontentchi ozi chiqarvoladi yashil qizil qilib
     * @param locomotiveId
     * @param reysId
     * @return
     */
    @Override
    public List<FreeVagonResponseDto> getFreeVagon(UUID locomotiveId, UUID reysId) {
        // get reys here
        List<FreeVagonResponseDto> list = new ArrayList<>();
        List<SeatsResponseDto> freeSeats = new ArrayList<>();
        int count = 0;
        List<VagonEntity> all = vagonRepository.findAllByLocomotiveId(locomotiveId);
        Double price = 0D;
        for (int i = 0; i < all.size(); i++) {
            List<SeatsResponseDto> seatsOfVagon = seatService.getSeatsOfVagon(all.get(i).getId());
            for (SeatsResponseDto seatsResponseDto : seatsOfVagon) {
                Boolean aBoolean = bookingsService.ticketIsSoldOrNot(seatsResponseDto.getSeatsId(), reysId);
                    if (!aBoolean) {
                        ///bu true qilganim frontentda yashil qilib sotilmagan dedb korsatish un
                        seatsResponseDto.setIsActive(true);
                        freeSeats.add(seatsResponseDto);
                        count++;
                        price = seatsResponseDto.getPrice();
                    }
                seatsResponseDto.setIsActive(false);
                freeSeats.add(seatsResponseDto);
            }
            list.add(new FreeVagonResponseDto(i,count,price, freeSeats));
            price = 0D;
        }
        return list;
    }

    @Override
    public List<VagonResponseDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<VagonEntity> allByIsActiveTrue = vagonRepository.findAllByIsActiveTrue(pageRequest);
        List<VagonEntity> content = allByIsActiveTrue.getContent();
        return parse(content);
    }
}
