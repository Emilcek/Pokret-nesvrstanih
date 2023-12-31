package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.TaskRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Task;
import com.progi.WildTrack.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepo;

    @Override
    public ResponseEntity getTasks() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(client.getExplorer().getTasks());
    }
}
