package qu4lizz.sni.forum.server.controllers;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.apache.coyote.BadRequestException;
import qu4lizz.sni.forum.server.exceptions.ForbiddenException;
import qu4lizz.sni.forum.server.models.dto.PendingCommentDTO;
import qu4lizz.sni.forum.server.models.requests.CommentUpdateContentRequest;
import qu4lizz.sni.forum.server.models.requests.CommentUpdateStatusRequest;
import qu4lizz.sni.forum.server.services.CommentService;
import qu4lizz.sni.forum.server.services.WAFService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final WAFService wafService;

    public CommentController(CommentService commentService, WAFService wafService) {
        this.commentService = commentService;
        this.wafService = wafService;
    }

    @GetMapping("/pending")
    public List<PendingCommentDTO> getAllPendingComments() {
        return commentService.getAllPendingComments();
    }

    @PutMapping("/status/{id}")
    public void updateCommentStatus(@PathVariable Integer id, @RequestBody @Valid CommentUpdateStatusRequest request, BindingResult bindingResult)
            throws ChangeSetPersister.NotFoundException, ForbiddenException, BadRequestException {
        wafService.checkRequest(bindingResult);
        commentService.updateCommentStatus(id, request);
    }

    @PutMapping("/content/{id}")
    public void updateCommentContent(@PathVariable Integer id, @RequestBody @Valid CommentUpdateContentRequest request, BindingResult bindingResult)
            throws ChangeSetPersister.NotFoundException, ForbiddenException, BadRequestException {
        wafService.checkRequest(bindingResult);
        commentService.updateCommentContent(id, request);
    }
}
