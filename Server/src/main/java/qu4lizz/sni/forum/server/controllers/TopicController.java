package qu4lizz.sni.forum.server.controllers;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.apache.coyote.BadRequestException;
import qu4lizz.sni.forum.server.exceptions.ForbiddenException;
import qu4lizz.sni.forum.server.models.dto.TopicDTO;
import qu4lizz.sni.forum.server.models.dto.TopicDetailsDTO;
import qu4lizz.sni.forum.server.models.dto.TopicPermissionsDTO;
import qu4lizz.sni.forum.server.models.dto.TopicWithoutImageDTO;
import qu4lizz.sni.forum.server.models.requests.CommentCreateRequest;
import qu4lizz.sni.forum.server.services.TopicService;
import qu4lizz.sni.forum.server.services.WAFService;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;
    private final WAFService wafService;

    public TopicController(TopicService topicService, WAFService wafService) {
        this.topicService = topicService;
        this.wafService = wafService;
    }

    @GetMapping
    public List<TopicDTO> getAll() {
        return topicService.getAll();
    }

    @GetMapping("/no-image")
    public List<TopicWithoutImageDTO> getAllWithoutImage() { return topicService.getAllWithoutImage(); }

    @GetMapping("/{id}")
    public TopicDetailsDTO getById(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        return topicService.getById(id);
    }

    @GetMapping("/{id}/permissions")
    public TopicPermissionsDTO getPermissionsById(@PathVariable Integer id) {
        return topicService.getPermissionsById(id);
    }

    @PostMapping("/comments")
    public void createComment(@RequestBody @Valid CommentCreateRequest request, BindingResult bindingResult) throws ForbiddenException, BadRequestException {
        wafService.checkRequest(bindingResult);
        topicService.createComment(request);
    }
}
