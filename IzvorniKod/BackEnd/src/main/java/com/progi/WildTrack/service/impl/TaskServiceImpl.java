package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ActionRepository;
import com.progi.WildTrack.dao.TaskRepository;
import com.progi.WildTrack.domain.Action;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Task;
import com.progi.WildTrack.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private ActionRepository actionRepo;

    @Override
    public ResponseEntity getTasks() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Task> ongoingTasks = taskRepo.findAllByExplorerAndTaskStatus(client.getExplorer(), "Ongoing");
        return ResponseEntity.ok(ongoingTasks);
    }

    @Override
    public ResponseEntity getTask(Long taskId) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepo.findByTaskId(taskId);
        if (task == null || !task.getExplorer().equals(client.getExplorer())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskRepo.findByTaskId(taskId));
    }

    @Override
    public ResponseEntity setTaskDone(Long taskId) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepo.findByTaskId(taskId);
        if (task == null || !task.getExplorer().equals(client.getExplorer())) {
            return ResponseEntity.notFound().build();
        }
        task.setTaskStatus("Done");
        taskRepo.save(task);
        Action action = task.getAction();
        boolean allDone = true;
        for (Task t : action.getTasks()) {
            if (!t.getTaskStatus().equals("Done")) {
                allDone = false;
                break;
            }
        }
        if (allDone) {
            action.setActionStatus("Done");
            actionRepo.save(action);
        }
        return ResponseEntity.ok().build();
    }
}
