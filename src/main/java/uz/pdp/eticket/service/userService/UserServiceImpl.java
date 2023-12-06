package uz.pdp.eticket.service.userService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.eticket.DTO.request.MailDto;
import uz.pdp.eticket.DTO.request.SignUpDto;
import uz.pdp.eticket.DTO.response.JwtResponse;
import uz.pdp.eticket.DTO.response.SubjectDto;
import uz.pdp.eticket.DTO.response.UserResponseDto;
import uz.pdp.eticket.entity.UserEntity;
import uz.pdp.eticket.entity.UserPassword;
import uz.pdp.eticket.exception.DataAlreadyExistsException;
import uz.pdp.eticket.exception.DataNotFoundException;
import uz.pdp.eticket.repository.PasswordRepository;
import uz.pdp.eticket.repository.UserRepository;
import uz.pdp.eticket.service.MailService;
import uz.pdp.eticket.service.jwt.AuthenticationService;
import uz.pdp.eticket.service.jwt.JwtService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final MailService mailService;

    @Override
    public void emailSend(UserEntity userEntity) {
        if(!userEntity.getIsActive()) {
            throw new AuthenticationCredentialsNotFoundException("User is not active");
        }
        String generatedString = RandomStringUtils.randomAlphanumeric(5);
        System.err.println("generatedString = " + generatedString);
        MailDto mailDto = new MailDto(generatedString, userEntity.getEmail());
        mailService.sendMail(mailDto);
        passwordRepository.save(new UserPassword(generatedString,userEntity, LocalDateTime.now(),3));
    }

    @Transactional
    @Override
    public UserResponseDto signUp(SignUpDto dto) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(dto.getEmail());
        if(optionalUser.isPresent()) {
            throw new DataAlreadyExistsException("User already exists with email: " + dto.getEmail());
        }

        UserEntity user = modelMapper.map(dto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        emailSend(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public JwtResponse signIn(String email, String password) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
        if(userEntity.getIsAuthenticated() && userEntity.getIsActive()) {
            if(passwordEncoder.matches(password, userEntity.getPassword())) {
                return new JwtResponse(jwtService.generateToken(userEntity));
            }
            throw new AuthenticationCredentialsNotFoundException("Password didn't match");
        }
        throw new AuthenticationCredentialsNotFoundException("Not verified");
    }

    @Override
    public String getVerificationCode(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
        emailSend(user);
        return "Verify code sent";
    }

    @Override
    public UserResponseDto verify(String email, String code) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
        UserPassword passwords = passwordRepository.getUserPasswordById(userEntity.getId(),code)
                .orElseThrow(()-> new DataNotFoundException("Password is not found"));
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime sentDate = passwords.getSentDate();
        Duration duration = Duration.between(sentDate, currentTime);
        long minutes = duration.toMinutes();
        if(minutes <= passwords.getExpiry()) {
            userEntity.setIsAuthenticated(true);
            userRepository.save(userEntity);
            return modelMapper.map(userEntity,UserResponseDto.class);
        }
        throw new AuthenticationCredentialsNotFoundException("Password is expired");
    }
    @Override
    public SubjectDto verifyToken(String token) {
        try{
            Jws<Claims> claimsJws = jwtService.extractToken(token);
            Claims claims = claimsJws.getBody();
            String subject = claims.getSubject();
            List<String> roles = (List<String>) claims.get("roles");
            return new SubjectDto(UUID.fromString(subject),roles);
        }catch (ExpiredJwtException e){
            throw new AuthenticationCredentialsNotFoundException("Token expired");
        }

    }

    @Override
    public UserResponseDto getById(UUID userId) {
        UserEntity userEntity = userRepository.findByIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
        return modelMapper.map(userEntity, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAll(String role) {
        List<UserEntity> users = userRepository.findAllByRoleAndIsActiveTrue(role);
        return users.stream().map(userEntity -> modelMapper.map(userEntity, UserResponseDto.class))
                .toList();
    }

    @Override
    public String deleteUser(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
        return "User deleted";
    }

    @Override
    public UserEntity findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("User not found with id: "+userId));
    }
}
