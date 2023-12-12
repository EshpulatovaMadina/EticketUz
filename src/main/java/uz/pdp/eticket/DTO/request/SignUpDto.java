package uz.pdp.eticket.DTO.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    private String name;
    private String surname;
    @Size(min = 4, message = "Password should have at least 4 characters")
    private String password;
    @Email(message = "Please provide a valid email address")
    private String email;

    @Past(message = "Please provide a valid date of birth")
    private LocalDate birthday;

    // Tug'ilgan sanani 16 yil oldin bo'lishini tekshirish
    @AssertTrue(message = "You must be at least 16 years old")
    private boolean isValidBirthday() {
        if (birthday == null) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate minimumValidDate = currentDate.minusYears(15);
        return !(birthday.isEqual(minimumValidDate) || birthday.isAfter(minimumValidDate));
    }
}
