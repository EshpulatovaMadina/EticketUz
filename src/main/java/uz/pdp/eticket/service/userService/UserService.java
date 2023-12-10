package uz.pdp.eticket.service.userService;
import uz.pdp.eticket.DTO.request.SignUpDto;
import uz.pdp.eticket.DTO.request.VerifyDto;
import uz.pdp.eticket.DTO.request.VerifyDtoP;
import uz.pdp.eticket.DTO.response.JwtResponse;
import uz.pdp.eticket.DTO.response.SubjectDto;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void emailSend(UserEntity userEntity);

    UserResponseDto signUp(SignUpDto dto);
    JwtResponse signIn(VerifyDtoP verifyDtoP);

    String getVerificationCode(String email);

    UserResponseDto verify(VerifyDto verifyDto);
    SubjectDto verifyToken(String token);

    UserResponseDto getById(UUID userId);

    List<UserResponseDto> getAll(String role);

    String deleteUser(UUID userId);

    UserEntity findById(UUID userId);

    JwtResponse getAccessToken(String refreshToken, UUID userId);

    String forgetPassword(VerifyDtoP verifyDtoP);
}
