package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ActionRepository;
import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.dao.TaskRepository;
import com.progi.WildTrack.domain.Action;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Explorer;
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
    @Autowired
    private ExplorerRepository explorerRepo;

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

        //postavljanje akcije u "Done" ako su rijeseni svi zadatci
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

        //postavljanje tragaca u "Available" ako su rijeseni svi zadatci
        allDone = true;
        for (Task t : client.getExplorer().getTasks()) {
            if (!t.getTaskStatus().equals("Done")) {
                allDone = false;
                break;
            }
        }
        if (allDone) {
            Explorer explorer = client.getExplorer();
            explorer.setExplorerStatus("Available");
            explorerRepo.save(explorer);
        }
        return ResponseEntity.ok().build();
    }
}
