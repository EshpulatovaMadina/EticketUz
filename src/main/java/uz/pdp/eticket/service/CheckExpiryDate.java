package uz.pdp.eticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.eticket.service.bookingService.BookingsService;

@Component
public class CheckExpiryDate {
    private BookingsService bookingsService;
    @Autowired
    @Scheduled(fixedRate = 60000)
    public void scheduleTaskUsingCronExpression() {
        bookingsService.checkExcpiryDate();
    }

}
