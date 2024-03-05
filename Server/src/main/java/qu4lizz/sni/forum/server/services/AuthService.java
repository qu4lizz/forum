package qu4lizz.sni.forum.server.services;

import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.exceptions.EmailAlreadyExistException;
import qu4lizz.sni.forum.server.exceptions.InvalidCredentialsException;
import qu4lizz.sni.forum.server.models.requests.LoginCodeRequest;
import qu4lizz.sni.forum.server.models.requests.LoginRequest;
import qu4lizz.sni.forum.server.models.requests.RegisterRequest;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;
import qu4lizz.sni.forum.server.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(LoginRequest request) throws InvalidCredentialsException {
    }

    public void register(RegisterRequest request) throws EmailAlreadyExistException {
    }

    public JwtAuthResponse loginWithCode(LoginCodeRequest request) throws InvalidCredentialsException {
        return new JwtAuthResponse("a");
    }
}
