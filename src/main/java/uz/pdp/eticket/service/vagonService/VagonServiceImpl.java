package uz.pdp.eticket.service.vagonService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.eticket.DTO.request.VagonCreateDto;
import uz.pdp.eticket.DTO.response.FreeVagonResponseDto;
import uz.pdp.eticket.DTO.response.SeatsResponseDto;
import uz.pdp.eticket.DTO.response.VagonResponseDto;
import uz.pdp.eticket.entity.LocomotiveEntity;
import uz.pdp.eticket.entity.VagonEntity;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.VagonRepository;
import uz.pdp.eticket.service.bookingService.BookingsService;
import uz.pdp.eticket.service.locomotiveService.LocomotiveService;
import uz.pdp.eticket.service.seatsService.SeatsService;

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
    private final SeatsService seatsService;
    private final BookingsService bookingsService;
    String  url = "TICKET_SERVICE";
    @Override
    public VagonResponseDto create(List<VagonCreateDto> dtos, Double seatPrice) {
        VagonEntity save = null;
        for (int i = 0; i < dtos.size(); i++) {
            if (vagonRepository.existsByNumber(dtos.get(i).getNumber())){
                throw new DataAlreadyExistsException("Such a digital wagon already exists ... . . Please give another number for this wagon.");
            }
            VagonEntity vagon = parse(dtos.get(i));
            save = vagonRepository.save(vagon);
            seatsService.create(vagon.getId(), seatPrice);
        }
        assert save != null;
        return parse(save);
    }

    @Override
    public List<VagonResponseDto> setLocomotive(List<UUID> vagonsId, UUID locomotiveId) {
        LocomotiveEntity locomotive = locomotiveService.findById(locomotiveId);
        Integer counted = vagonRepository.countAllByLocomotiveId(locomotiveId);
        List<VagonEntity> list = new ArrayList<>();
        for (UUID vagonId : vagonsId) {
            VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
            if (vagon.getLocomotive() != null){
                List<VagonEntity> all = vagonRepository.findAllByLocomotiveIdOrderByNumberOnTheTrain(vagon.getLocomotive().getId());
                updateVagonNumberOnTheTrain(all, vagon.getNumberOnTheTrain());
            // buyerda muoomo bordek anu osha vagon hali trainda haliyam boladiku shu qolib ketmaydimi yana. unikini ham o'zgartirib qo'ymaymizmi
            }
            vagon.setLocomotive(locomotive);
            vagon.setNumberOnTheTrain(counted+1);
            vagonRepository.save(vagon);
            list.add(vagon);
        }
       return parse(list);
    }

    private void updateVagonNumberOnTheTrain(List<VagonEntity> all, Integer numberOnTheTrain) {
        for (int i = numberOnTheTrain; i < all.size(); i++) {
            all.get(i + 1).setNumberOnTheTrain(i);
            vagonRepository.save(all.get(i+1));
        }
    }


    @Override
    public String isInActive(UUID vagonId) {
        VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
        vagon.setIsActive(false);
        vagonRepository.save(vagon);
        return "Successfully";
    }

//    @Override
//    public VagonResponseDto update(UUID vagonId, VagonCreateDto dto) {
//        VagonEntity vagon = vagonRepository.findById(vagonId).orElseThrow(() -> new DataNotFoundException("Vagon not found"));
////        LocomotiveEntity locomotive = locomotiveService.findById(dto.getLocomotiveId());
////        vagon.setLocomotive(locomotive);
//        vagon.setNumber(dto.getNumber());
//        vagon.setVagonTypes(dto.getVagonTypes());
////        vagon.setIsActive(true);
//        vagonRepository.save(vagon);
//        return parse(vagon);
//
//    }

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
       return modelMapper.map(dto, VagonEntity.class);
    }

    private List<VagonResponseDto> parse(List<VagonEntity> entities){
        List<VagonResponseDto> list = new ArrayList<>();
        for (VagonEntity vagon : entities) {
            VagonResponseDto map = modelMapper.map(vagon, VagonResponseDto.class);
            map.setLocomotiveId(vagon.getLocomotive().getId());
            list.add(map);
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
            List<SeatsResponseDto> seatsOfVagon = seatsService.getSeatsOfVagon(all.get(i).getId());
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
}
