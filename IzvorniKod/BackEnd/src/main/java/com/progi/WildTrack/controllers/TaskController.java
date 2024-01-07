package com.progi.WildTrack.controllers;

import com.progi.WildTrack.service.TaskCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class TaskController {

    private final TaskCommentService taskCommentService;

    @PutMapping("/{taskId}/comment")
    public ResponseEntity addComment(@PathVariable Long taskId, @RequestBody String comment) {
        return taskCommentService.addComment(taskId, comment);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity getTaskComments(@PathVariable Long taskId) {
        return taskCommentService.getTaskComments(taskId);
    }
}
