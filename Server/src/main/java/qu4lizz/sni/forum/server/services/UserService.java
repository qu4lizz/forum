package qu4lizz.sni.forum.server.services;

import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.models.responses.UsernameResponse;
import qu4lizz.sni.forum.server.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UsernameResponse getUsernameFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return new UsernameResponse(username);
    }
}
