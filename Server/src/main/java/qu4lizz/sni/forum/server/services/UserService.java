package qu4lizz.sni.forum.server.services;

import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
