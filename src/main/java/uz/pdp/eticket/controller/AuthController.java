package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.SignUpDto;
import uz.pdp.eticket.DTO.response.JwtResponse;
import uz.pdp.eticket.DTO.response.SubjectDto;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.service.userService.UserServiceImpl;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Auth Management")
@SecurityRequirement(name = "Bearer Authentication")
public class AuthController {
    private final UserServiceImpl userServiceImpl;

    @PermitAll
    @PostMapping("/sign-up")
    public UserResponseDto signUp(@RequestBody SignUpDto dto) {
        return userServiceImpl.signUp(dto);
    }


    @PermitAll
    @GetMapping("/sign-in")
    public JwtResponse signIn(
            @RequestParam String email,
            @RequestParam String password
            ) {
        return userServiceImpl.signIn(email, password);
    }


    @PermitAll
    @GetMapping("/get-verification-code")
    public String sendVerifyCode(@RequestParam String email) {
        return userServiceImpl.getVerificationCode(email);
    }


    @Operation(
            description = "This API is used for verifying",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )


    @PermitAll
    @GetMapping("/verify")
    public UserResponseDto verify(@RequestParam String email, @RequestParam String code) {
        UserResponseDto verify = userServiceImpl.verify(email, code);
        return verify;
    }


    @PermitAll
    @GetMapping("/verify-token")
    public SubjectDto verifyToken (@RequestParam String token) {
        return userServiceImpl.verifyToken(token);
    }

}
