package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.TaskCommentRepository;
import com.progi.WildTrack.dao.TaskRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Task;
import com.progi.WildTrack.domain.TaskComment;
import com.progi.WildTrack.service.TaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {
    @Autowired
    private TaskCommentRepository taskCommentRepo;
    @Autowired
    private TaskRepository taskRepo;

    @Override
    public ResponseEntity addComment(Long taskId, String comment) {
        Task task = taskRepo.findByTaskId(taskId);
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        TaskComment taskComment = TaskComment.builder()
                .taskDescription(comment)
                .task(task)
                .client(client)
                .build();
        taskCommentRepo.save(taskComment);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity getTaskComments(Long taskId) {
        Task task = taskRepo.findByTaskId(taskId);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskCommentRepo.findAllByTask(task));
    }
}
