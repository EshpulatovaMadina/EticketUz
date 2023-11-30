package uz.pdp.eticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.DTO.request.MailDto;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    public void sendMail(MailDto dto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(dto.getEmail());
        simpleMailMessage.setText(dto.getMessage());
        mailSender.send(simpleMailMessage);
    }

}
