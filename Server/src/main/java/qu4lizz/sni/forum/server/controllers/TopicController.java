package qu4lizz.sni.forum.server.controllers;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import qu4lizz.sni.forum.server.exceptions.UnauthorizedException;
import qu4lizz.sni.forum.server.models.dto.TopicDTO;
import qu4lizz.sni.forum.server.models.dto.TopicDetailsDTO;
import qu4lizz.sni.forum.server.models.dto.TopicPermissionsDTO;
import qu4lizz.sni.forum.server.models.entities.TopicEntity;
import qu4lizz.sni.forum.server.models.requests.CommentCreateRequest;
import qu4lizz.sni.forum.server.services.TopicService;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public List<TopicDTO> getAll() {
        return topicService.getAll();
    }

    @GetMapping("/{id}")
    public TopicDetailsDTO getById(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        return topicService.getById(id);
    }

    @GetMapping("/{id}/permissions")
    public TopicPermissionsDTO getPermissionsById(@PathVariable Integer id) {
        return topicService.getPermissionsById(id);
    }

    @PostMapping("/comments")
    public void createComment(@RequestBody @Valid CommentCreateRequest request) throws UnauthorizedException {
        topicService.createComment(request);
    }
}
