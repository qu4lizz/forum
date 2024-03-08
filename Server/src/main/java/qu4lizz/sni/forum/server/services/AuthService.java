package qu4lizz.sni.forum.server.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.Utils;
import qu4lizz.sni.forum.server.exceptions.AlreadyExistsException;
import qu4lizz.sni.forum.server.exceptions.InvalidCredentialsException;
import qu4lizz.sni.forum.server.exceptions.UnauthorizedException;
import qu4lizz.sni.forum.server.models.CodeToRemove;
import qu4lizz.sni.forum.server.models.dto.JwtUser;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;
import qu4lizz.sni.forum.server.models.entities.UserEntity;
import qu4lizz.sni.forum.server.models.enums.Role;
import qu4lizz.sni.forum.server.models.enums.Status;
import qu4lizz.sni.forum.server.models.requests.LoginCodeRequest;
import qu4lizz.sni.forum.server.models.requests.LoginRequest;
import qu4lizz.sni.forum.server.models.requests.RegisterRequest;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;
import qu4lizz.sni.forum.server.models.responses.LoginResponse;
import qu4lizz.sni.forum.server.repositories.PermissionRepository;
import qu4lizz.sni.forum.server.repositories.TopicRepository;
import qu4lizz.sni.forum.server.repositories.UserRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TopicRepository topicRepository;
    private final PermissionRepository permissionRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;
    private static final List<CodeToRemove> codesToRemove = new ArrayList<>();

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, TopicRepository topicRepository,
                       PermissionRepository permissionRepository, EmailService emailService,
                       JwtService jwtService, JwtUserDetailsService jwtUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.topicRepository = topicRepository;
        this.permissionRepository = permissionRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    public LoginResponse login(LoginRequest request) throws InvalidCredentialsException, UnauthorizedException {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            JwtUser jwtUser = (JwtUser) auth.getPrincipal();

            UserEntity userEntity = userRepository.findById(jwtUser.getId()).orElseThrow();

            if (userEntity.getStatus().equals(Status.REQUESTED)) {
                throw new UnauthorizedException("Your account still isn't approved");
            } else if (userEntity.getStatus().equals(Status.REJECTED)) {
                throw new UnauthorizedException("Your account has been rejected, try registering again");
            }

            String code = Utils.generateRandomCode();
            userEntity.setLoginCode(code);
            userRepository.save(userEntity);

            codesToRemove.add(new CodeToRemove(code, Instant.now().plus(Duration.ofMinutes(15)), userEntity.getId()));

            emailService.sendLoginCode(userEntity.getEmail(), code);

            return new LoginResponse(userEntity.getId());
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            throw new InvalidCredentialsException();
        }
    }

    public JwtAuthResponse loginWithCode(LoginCodeRequest request) throws InvalidCredentialsException, NotFoundException, UnauthorizedException {
        UserEntity userEntity = userRepository.findById(request.getId()).orElseThrow(NotFoundException::new);

        if (userEntity.getStatus().equals(Status.REQUESTED)) {
            throw new UnauthorizedException("Your account still isn't approved");
        } else if (userEntity.getStatus().equals(Status.REJECTED)) {
            throw new UnauthorizedException("Your account has been rejected, try registering again");
        }

        if (userEntity.getLoginCode().equals(request.getCode())) {
            JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(userEntity.getUsername());

            return new JwtAuthResponse(jwtService.generateToken(jwtUser), jwtUser.getUsername());
        }
        else throw new InvalidCredentialsException("Incorrect login code");
    }

    public void register(RegisterRequest request) throws AlreadyExistsException {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AlreadyExistsException("Email already exists");
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AlreadyExistsException("Username already exists");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity.setStatus(Status.REQUESTED);

        UserEntity saved = userRepository.save(userEntity);

        var topics = topicRepository.findAll();
        for (var topic : topics) {
            // everyone has READ permission
            // with this line adding WRITE permission to everyone (writing comments)
            // only admins and moderators have UPDATE and DELETE permissions
            permissionRepository.save(new PermissionEntity(saved.getId(), topic.getId()));
        }
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5 minutes
    private void resetLoginCodes() {
        System.out.println("scheduler " + codesToRemove.size());
        List<CodeToRemove> removable = new ArrayList<>();
        Instant now = Instant.now();

        for (var toRemove : codesToRemove) {
            if (now.isAfter(toRemove.getTimestamp())) {
                removable.add(toRemove);
                var userEntity = userRepository.findById(toRemove.getUserId());
                if (userEntity.isPresent() && userEntity.get().getLoginCode().equals(toRemove.getCode())) {
                    userEntity.get().setLoginCode(null);
                    userRepository.save(userEntity.get());
                }
            }
        }

        codesToRemove.removeAll(removable);
    }
}
