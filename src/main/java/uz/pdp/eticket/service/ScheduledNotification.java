package uz.pdp.eticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.eticket.DTO.request.MailDto;
import uz.pdp.eticket.entity.UserEntity;
import uz.pdp.eticket.repository.UserRepository;
import uz.pdp.eticket.service.bookingService.BookingsService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledNotification {
    private final BookingsService bookingsService;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Scheduled(fixedRate = 60000)
    public void scheduleTaskUsingCronExpression() {
        log.warn(bookingsService.checkExcpiryDate());
    }

    @Scheduled(fixedRate = 50000)
    public void warnAllUsers() {
        List<UserEntity> userEntities = userRepository.findAllByStartDate(LocalDateTime.now().plusDays(1));
        userEntities.forEach(item -> {
            MailDto mailDto = new MailDto("The flight for which you bought a ticket will depart in 1 day", item.getEmail());
            mailService.sendMail(mailDto);
        });
    }

}
