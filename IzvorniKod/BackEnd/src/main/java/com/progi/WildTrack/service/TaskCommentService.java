package com.progi.WildTrack.service;

import org.springframework.http.ResponseEntity;

public interface TaskCommentService {
    ResponseEntity addComment(Long taskId, String comment);

    ResponseEntity getTaskComments(Long taskId);
}
