package uz.pdp.eticket.service.userService;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.eticket.DTO.request.SignUpDto;
import uz.pdp.eticket.DTO.response.JwtResponse;
import uz.pdp.eticket.DTO.response.SubjectDto;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void emailSend(UserEntity userEntity);

    UserResponseDto signUp(SignUpDto dto);
    JwtResponse signIn(String email, String password);

    String getVerificationCode(String email);

    UserResponseDto verify(String email, String code);
    SubjectDto verifyToken(String token);

    UserResponseDto getById(UUID userId);

    List<UserResponseDto> getAll(String role);

    String deleteUser(UUID userId);

    UserEntity findById(UUID userId);
}
