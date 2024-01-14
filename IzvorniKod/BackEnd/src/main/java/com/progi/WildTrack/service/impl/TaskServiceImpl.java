package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ActionRepository;
import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.dao.TaskRepository;
import com.progi.WildTrack.domain.Action;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Task;
import com.progi.WildTrack.dto.ResponseTaskDTO;
import com.progi.WildTrack.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<ResponseTaskDTO> mappedTasks = ongoingTasks.stream().map(ResponseTaskDTO::new).toList();
        return ResponseEntity.ok(mappedTasks);
    }

    @Override
    public ResponseEntity getTask(Long taskId) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepo.findByTaskId(taskId);
        if (task == null || !task.getExplorer().getExplorerName().equals(client.getClientName())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ResponseTaskDTO(task));
    }

    @Override
    @Transactional
    public ResponseEntity setTaskDone(Long taskId) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepo.findByTaskId(taskId);
        if (task == null || task.getTaskStatus().equals("Done") || !task.getExplorer().getExplorerName().equals(client.getClientName())) {
            return ResponseEntity.notFound().build();
        }

        //postavljanje akcije u "Done" ako su rijeseni svi zadatci
        Action action = task.getAction();
        boolean actionDone = true;
        for (Task t : action.getTasks()) {
            if (!t.getTaskStatus().equals("Done") && !(Objects.equals(t.getTaskId(), taskId))) {
                System.out.println("Task " + t.getTaskId() + " in action not done");
                actionDone = false;
                break;
            }
        }

        //postavljanje tragaca u "Available" ako su rijeseni svi zadatci
        boolean tasksDone = true;
        for (Task t : client.getExplorer().getTasks()) {
            if (!t.getTaskStatus().equals("Done") && !(Objects.equals(t.getTaskId(), taskId))) {
                System.out.println("Task " + t.getTaskId() + " not done");
                tasksDone = false;
                break;
            }
        }
        if (tasksDone) {
            System.out.println("All tasks for explorer done");
            Explorer explorer = client.getExplorer();
            explorer.setExplorerStatus("Available");
            explorerRepo.save(explorer);
        }
        if (actionDone) {
            System.out.println("All tasks for action done");
            action.setActionStatus("Done");
            actionRepo.save(action);
        }

        task.setTaskStatus("Done");
        taskRepo.save(task);
        return ResponseEntity.ok().build();
    }
}
