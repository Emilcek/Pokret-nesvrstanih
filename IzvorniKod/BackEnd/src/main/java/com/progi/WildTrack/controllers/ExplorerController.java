package com.progi.WildTrack.controllers;


import com.progi.WildTrack.service.TaskCommentService;
import com.progi.WildTrack.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/explorer")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ExplorerController {

    private final TaskService taskService;
    private final TaskCommentService taskCommentService;

    @GetMapping("/tasks")
    public ResponseEntity getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @PostMapping("/task/{taskId}/done")
    public ResponseEntity setTaskDone(@PathVariable Long taskId) {
        return taskService.setTaskDone(taskId);
    }
}
