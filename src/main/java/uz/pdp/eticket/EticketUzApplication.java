package uz.pdp.eticket;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;




//@SecurityScheme(
//		name = "Bearer Authentication",
//		description = "JWT authentication required",
//		scheme = "bearer",
//		type = SecuritySchemeType.HTTP,
//		bearerFormat = "JWT",
//		in = SecuritySchemeIn.HEADER
//
//)
//@OpenAPIDefinition(security = @SecurityRequirement(name = "Bearer Authentication"))
@SpringBootApplication
//@EnableTransactionManagement
@EnableScheduling
public class EticketUzApplication {
	public static void main(String[] args) {
		SpringApplication.run(EticketUzApplication.class, args);
	}

}
