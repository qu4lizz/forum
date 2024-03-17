package qu4lizz.sni.forum.server.controllers;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.apache.coyote.BadRequestException;
import qu4lizz.sni.forum.server.models.dto.UserDTO;
import qu4lizz.sni.forum.server.models.requests.UpdateUserRequest;
import qu4lizz.sni.forum.server.models.responses.UsernameResponse;
import qu4lizz.sni.forum.server.services.UserService;
import qu4lizz.sni.forum.server.services.WAFService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final WAFService wafService;

    public UserController(UserService userService, WAFService wafService) {
        this.userService = userService;
        this.wafService = wafService;
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
    public void updateUser(@PathVariable Integer id, @RequestBody @Valid UpdateUserRequest request, BindingResult bindingResult) throws ChangeSetPersister.NotFoundException, BadRequestException {
        wafService.checkRequest(bindingResult);
        userService.updateUser(id, request);
    }
}
