package uz.pdp.eticket.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.service.userService.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @GetMapping("/me")
    public UserResponseDto getById(Principal principal) {
        return userServiceImpl.getById(UUID.fromString(principal.getName()));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public List<UserResponseDto> getAll(@RequestParam String role) {
        return userServiceImpl.getAll(role);
    }

    // this api for only Admin or super admin
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    public String delete(@PathVariable UUID userId) {
        return userServiceImpl.deleteUser(userId);
    }
}
