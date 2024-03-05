package qu4lizz.sni.forum.server.controllers;

import org.springframework.web.bind.annotation.*;
import qu4lizz.sni.forum.server.exceptions.EmailAlreadyExistException;
import qu4lizz.sni.forum.server.exceptions.InvalidCredentialsException;
import qu4lizz.sni.forum.server.models.requests.LoginCodeRequest;
import qu4lizz.sni.forum.server.models.requests.LoginRequest;
import qu4lizz.sni.forum.server.models.requests.RegisterRequest;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;
import qu4lizz.sni.forum.server.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) throws InvalidCredentialsException {
        authService.login(request);
    }

    @PostMapping("/login-with-code")
    public JwtAuthResponse loginWithCode(@RequestBody LoginCodeRequest request) throws InvalidCredentialsException {
        JwtAuthResponse res = authService.loginWithCode(request);
        return res;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) throws EmailAlreadyExistException {
        authService.register(request);
    }


}
