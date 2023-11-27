package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.SignUpDto;
import uz.pdp.eticket.DTO.response.JwtResponse;
import uz.pdp.eticket.DTO.response.SubjectDto;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PermitAll
    @PostMapping("sign-up")
    public UserResponseDto signUp(@RequestBody SignUpDto dto) {
        return userService.signUp(dto);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PermitAll
    @GetMapping("sign-in")
    public JwtResponse signIn(
            @RequestParam String email,
            @RequestParam String password
            ) {
        return userService.signIn(email, password);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PermitAll
    @GetMapping("/get-verification-code")
    public String sendVerifyCode(@RequestParam String email) {
        return userService.getVerificationCode(email);
    }



    @Operation(
            description = "This API is used for verifying",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PermitAll
    @GetMapping("/verify")
    public UserResponseDto verify(@RequestParam String email, @RequestParam String code) {
        UserResponseDto verify = userService.verify(email, code);
        return verify;
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PermitAll
    @GetMapping("/verify-token")
    public SubjectDto verifyToken (@RequestParam String token) {
        return userService.verifyToken(token);
    }

}
