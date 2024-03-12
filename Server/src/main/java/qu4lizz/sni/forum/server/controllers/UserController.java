package qu4lizz.sni.forum.server.controllers;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import qu4lizz.sni.forum.server.models.dto.UserDTO;
import qu4lizz.sni.forum.server.models.requests.UpdateUserRequest;
import qu4lizz.sni.forum.server.models.responses.UsernameResponse;
import qu4lizz.sni.forum.server.services.UserService;

import java.util.List;

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

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) throws ChangeSetPersister.NotFoundException {
        userService.updateUser(id, request);
    }
}
