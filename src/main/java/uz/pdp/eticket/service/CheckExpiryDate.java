package uz.pdp.eticket.service;

import org.springframework.scheduling.annotation.Scheduled;
import uz.pdp.eticket.service.bookingService.BookingsService;

public class CheckExpiryDate {
    private BookingsService bookingsService;
    @Scheduled(fixedRate = 60000)
    public void scheduleTaskUsingCronExpression() {
        bookingsService.checkExcpiryDate();
    }

}
