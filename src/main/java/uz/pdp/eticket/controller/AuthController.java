package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.ForgetDto;
import uz.pdp.eticket.DTO.request.SignUpDto;
import uz.pdp.eticket.DTO.request.VerifyDto;
import uz.pdp.eticket.DTO.request.VerifyDtoP;
import uz.pdp.eticket.DTO.response.JwtResponse;
import uz.pdp.eticket.DTO.response.SubjectDto;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.service.userService.UserService;
import uz.pdp.eticket.service.userService.UserServiceImpl;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag( name = "Auth Management")
@SecurityRequirement( name = "Bearer Authentication")
public class AuthController {
    private final UserService userService;


    // api to get new access token with refresh token
    @PostMapping("/access-token")
    public String getAccessToken(@RequestBody String refreshToken, Principal principal) {
        return userService.getAccessToken(refreshToken, UUID.fromString(principal.getName()));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetDto forgetDto) {
        return ResponseEntity.ok(userService.forgetPassword(forgetDto));
    }

    @PermitAll
    @PostMapping("/sign-up")
    public UserResponseDto signUp(@Valid @RequestBody SignUpDto dto) {
        return userService.signUp(dto);
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody VerifyDtoP verifyDtoP) {
        return userService.signIn(verifyDtoP);
    }

    @PermitAll
    @PostMapping("/get-verification-code")
    public String sendVerifyCode(@RequestBody String email) {
        return userService.getVerificationCode(email);
    }

    @Operation(
            description = "This API is used for verifying",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PermitAll
    @PostMapping("/verify")
    public UserResponseDto verify(@RequestBody VerifyDto verifyDto) {
        return userService.verify(verifyDto);
    }

    @PermitAll
    @GetMapping("/verify-token")
    public SubjectDto verifyToken (@RequestBody String token) {
        return userService.verifyToken(token);
    }

}
