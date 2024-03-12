package qu4lizz.sni.forum.server.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.models.dto.UserDTO;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;
import qu4lizz.sni.forum.server.models.entities.UserEntity;
import qu4lizz.sni.forum.server.models.requests.UpdateUserRequest;
import qu4lizz.sni.forum.server.models.responses.UsernameResponse;
import qu4lizz.sni.forum.server.repositories.PermissionRepository;
import qu4lizz.sni.forum.server.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PermissionRepository permissionRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.modelMapper = modelMapper;
    }


    public UsernameResponse getUsernameFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return new UsernameResponse(username);
    }

    public List<UserDTO> getUsers() {
        var users = userRepository.findAll();

        return users.stream().map(u -> modelMapper.map(u, UserDTO.class)).toList();
    }

    public void updateUser(Integer id, UpdateUserRequest request) throws ChangeSetPersister.NotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (request.getRole() != null)
            userEntity.setRole(request.getRole());
        if (request.getStatus() != null)
            userEntity.setStatus(request.getStatus());
        if (request.getPermissions() != null) {
            for (var p : request.getPermissions()) {
                PermissionEntity permissionEntity = permissionRepository.findById(p.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
                permissionEntity.setUpdate(p.getUpdate());
                permissionEntity.setDelete(p.getDelete());
                permissionEntity.setWrite(p.getWrite());
            }
        }

        userRepository.save(userEntity);
    }
}
