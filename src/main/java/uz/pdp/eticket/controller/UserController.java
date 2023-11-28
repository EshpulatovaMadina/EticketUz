package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public UserResponseDto getById(@PathVariable UUID userId) {
        return userService.getById(userId);
    }


    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    @GetMapping("/get-all")
    public List<UserResponseDto> getAll(@RequestParam String role) {
        return userService.getAll(role);
    }


    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public String delete(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }
}
