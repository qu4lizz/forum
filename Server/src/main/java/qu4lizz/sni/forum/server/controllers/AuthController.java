package qu4lizz.sni.forum.server.controllers;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import qu4lizz.sni.forum.server.exceptions.AlreadyExistsException;
import qu4lizz.sni.forum.server.exceptions.InvalidCredentialsException;
import qu4lizz.sni.forum.server.exceptions.UnauthorizedException;
import qu4lizz.sni.forum.server.models.requests.LoginCodeRequest;
import qu4lizz.sni.forum.server.models.requests.LoginRequest;
import qu4lizz.sni.forum.server.models.requests.RegisterRequest;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;
import qu4lizz.sni.forum.server.models.responses.LoginResponse;
import qu4lizz.sni.forum.server.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) throws InvalidCredentialsException, UnauthorizedException {
        return authService.login(request);
    }

    @PostMapping("/login-code")
    public JwtAuthResponse loginWithCode(@RequestBody @Valid LoginCodeRequest request) throws InvalidCredentialsException, UnauthorizedException, ChangeSetPersister.NotFoundException {
        return authService.loginWithCode(request);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest request) throws AlreadyExistsException {
        authService.register(request);
    }


}
