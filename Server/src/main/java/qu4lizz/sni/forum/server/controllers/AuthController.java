package qu4lizz.sni.forum.server.controllers;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qu4lizz.sni.forum.server.exceptions.UnauthorizedException;
import qu4lizz.sni.forum.server.models.requests.LoginCodeRequest;
import qu4lizz.sni.forum.server.models.requests.LoginRequest;
import qu4lizz.sni.forum.server.models.requests.RegisterRequest;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;
import qu4lizz.sni.forum.server.models.responses.LoginResponse;
import qu4lizz.sni.forum.server.services.AuthService;
import qu4lizz.sni.forum.server.services.WAFService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final WAFService wafService;

    public AuthController(AuthService authService, WAFService wafService) {
        this.authService = authService;
        this.wafService = wafService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request, BindingResult bindingResult) throws UnauthorizedException, BadRequestException {
        wafService.checkRequest(bindingResult);
        return authService.login(request);
    }

    @PostMapping("/login-code")
    public JwtAuthResponse loginWithCode(@RequestBody @Valid LoginCodeRequest request, BindingResult bindingResult) throws UnauthorizedException, ChangeSetPersister.NotFoundException, BadRequestException {
        wafService.checkRequest(bindingResult);
        return authService.loginWithCode(request);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult) throws BadRequestException {
        wafService.checkRequest(bindingResult);
        authService.register(request);
    }


}
