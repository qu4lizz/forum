package qu4lizz.sni.forum.server.controllers;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qu4lizz.sni.forum.server.models.dto.TopicDTO;
import qu4lizz.sni.forum.server.models.dto.TopicDetailsDTO;
import qu4lizz.sni.forum.server.models.entities.TopicEntity;
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
}
