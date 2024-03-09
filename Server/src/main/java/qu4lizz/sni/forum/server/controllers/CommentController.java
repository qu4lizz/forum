package qu4lizz.sni.forum.server.controllers;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import qu4lizz.sni.forum.server.exceptions.ForbiddenException;
import qu4lizz.sni.forum.server.models.dto.PendingCommentDTO;
import qu4lizz.sni.forum.server.models.requests.CommentUpdateContentRequest;
import qu4lizz.sni.forum.server.models.requests.CommentUpdateStatusRequest;
import qu4lizz.sni.forum.server.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/pending")
    public List<PendingCommentDTO> getAllPendingComments() {
        return commentService.getAllPendingComments();
    }

    @PutMapping("/status/{id}")
    public void updateCommentStatus(@PathVariable Integer id, @RequestBody @Valid CommentUpdateStatusRequest request)
            throws ChangeSetPersister.NotFoundException, ForbiddenException {
        commentService.updateCommentStatus(id, request);
    }

    @PutMapping("/content/{id}")
    public void updateCommentContent(@PathVariable Integer id, @RequestBody @Valid CommentUpdateContentRequest request)
            throws ChangeSetPersister.NotFoundException, ForbiddenException {
        commentService.updateCommentContent(id, request);
    }
}
