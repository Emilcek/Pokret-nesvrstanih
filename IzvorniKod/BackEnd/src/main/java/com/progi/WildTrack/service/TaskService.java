package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Task;
import org.springframework.http.ResponseEntity;

public interface TaskService {
    ResponseEntity getTasks();

    ResponseEntity getTask(Long taskId);


    ResponseEntity setTaskDone(Long taskId);
}
