package qu4lizz.sni.forum.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qu4lizz.sni.forum.server.models.dto.PermissionDTO;
import qu4lizz.sni.forum.server.services.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/my")
    public List<PermissionDTO> getMyPermissions() {
        return permissionService.getMyPermissions();
    }
}
