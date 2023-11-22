package uz.pdp.eticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EticketUzApplication {

	public static void main(String[] args) {
		SpringApplication.run(EticketUzApplication.class, args);
	}

}
