package uz.pdp.eticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.request.SignUpDto;
import uz.pdp.eticket.DTO.response.JwtResponse;
import uz.pdp.eticket.DTO.response.SubjectDto;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.service.userService.UserServiceImpl;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag( name = "Auth Management")
@SecurityRequirement( name = "Bearer Authentication")
public class AuthController {
    private final UserServiceImpl userService;


    // api to get new access token with refresh token
    @GetMapping("/access-token")
    public JwtResponse getAccessToken(@RequestParam String refreshToken, Principal principal) {
        return userService.getAccessToken(refreshToken, UUID.fromString(principal.getName()));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam String email, @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.forgetPassword(email,newPassword));
    }

    @PermitAll
    @PostMapping("/sign-up")
    public UserResponseDto signUp(@RequestBody SignUpDto dto) {
        return userService.signUp(dto);
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(
            @RequestParam String email,
            @RequestParam String password
            ) {
        return userService.signIn(email, password);
    }

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
    @PermitAll
    @PostMapping("/verify/{email}/{code}")
    public UserResponseDto verify(@PathVariable String email, @PathVariable String code) {
        return userService.verify(email, code);
    }

    @PermitAll
    @GetMapping("/verify-token")
    public SubjectDto verifyToken (@RequestParam String token) {
        return userService.verifyToken(token);
    }

}
