package qu4lizz.sni.forum.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qu4lizz.sni.forum.server.models.responses.UsernameResponse;
import qu4lizz.sni.forum.server.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username")
    public UsernameResponse getUsername() {
        return userService.getUsernameFromContext();
    }
}
