package qu4lizz.sni.forum.server.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.models.dto.JwtUser;
import qu4lizz.sni.forum.server.models.dto.PermissionDTO;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;
import qu4lizz.sni.forum.server.repositories.PermissionRepository;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final ModelMapper modelMapper;

    public PermissionService(PermissionRepository permissionRepository, JwtUserDetailsService jwtUserDetailsService, ModelMapper modelMapper) {
        this.permissionRepository = permissionRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.modelMapper = modelMapper;
    }

    public List<PermissionDTO> getMyPermissions() {
        JwtUser jwtUser = jwtUserDetailsService.getUserFromJwt();

        List<PermissionEntity> permissionEntityList = permissionRepository.findAllByIdUser(jwtUser.getId());

        return permissionEntityList.stream().map(p -> modelMapper.map(p, PermissionDTO.class)).toList();
    }
}
