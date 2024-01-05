package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.*;
import com.progi.WildTrack.domain.*;
import com.progi.WildTrack.dto.CreateRequestDTO;
import com.progi.WildTrack.dto.ExplorerTaskDTO;
import com.progi.WildTrack.dto.TaskDTO;
import com.progi.WildTrack.dto.ActionDTO;
import com.progi.WildTrack.service.ActionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ActionRepository actionRepo;
    @Autowired
    private StationRepository stationRepo;
    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private ExplorerRepository explorerRepo;

    @Override
    public ResponseEntity getActions() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Researcher researcher = client.getResearcher();
        List<Action> actions = actionRepo.findAllByResearcher(researcher);
        return ResponseEntity.ok(actions.stream().map(ActionDTO::new).toList());
    }

    @Override
    public ResponseEntity getAction(Long actionId) {
        Action action = actionRepo.findByActionId(actionId);
        return ResponseEntity.ok(new ActionDTO(action));
    }

    @Override
    @Transactional
    public ResponseEntity createRequest(CreateRequestDTO request) {
        System.out.println(request);
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Researcher researcher = client.getResearcher();
        Station station = stationRepo.findByStationName(request.getStation());
        System.out.println("station " + station.getStationName());
        StationLead stationLead = station.getStationLead();
        if (stationLead == null || researcher == null) {
            return ResponseEntity.badRequest().build();
        }
        Action action = Action.builder()
                .actionStatus("Pending")
                .researcher(researcher)
                .stationLead(stationLead)
                .build();
        actionRepo.save(action);
        for(TaskDTO task : request.getTasks()) {
            Vehicle vehicle = (Vehicle) vehicleRepo.findByVehicleType(task.getTaskVehicle()).orElseThrow();
            Task build = new Task(task, vehicle, action);
            taskRepo.save(build);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity acceptRequest(Long actionId, List<ExplorerTaskDTO> explorerTasks) {
        Action action = actionRepo.findByActionId(actionId);
        action.setActionStatus("Accepted");
        actionRepo.save(action);
        for (ExplorerTaskDTO explorerTask : explorerTasks) {
            Explorer explorer = explorerRepo.findByExplorerName(explorerTask.getExplorerName());
            if (explorer == null) {
                return ResponseEntity.notFound().build();
            }
            explorer.setExplorerStatus("Unavailable");
            explorer.getActions().add(action);
            explorerRepo.save(explorer);

            Task task = taskRepo.findByTaskId(explorerTask.getTaskId());
            if (task == null) {
                return ResponseEntity.notFound().build();
            }
            task.setExplorer(explorer);
            task.setAction(action);
            taskRepo.save(task);

            action.getExplorers().add(explorer);
            actionRepo.save(action);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity getResearcherRequests() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Researcher researcher = client.getResearcher();
        List<Action> requests = actionRepo.findAllByResearcher(researcher).stream().filter(action -> action.getActionStatus().equals("Pending")).toList();
        return ResponseEntity.ok(requests);
    }

    @Override
    public ResponseEntity declineRequest(Long actionId) {
        Action action = actionRepo.findByActionId(actionId);
        if (action == null) {
            return ResponseEntity.notFound().build();
        }
        action.setActionStatus("Declined");
        actionRepo.save(action);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity getStationLeadRequests() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StationLead stationLead = client.getStationLead();
        List<Action> requests = actionRepo.findAllByStationLead(stationLead).stream().filter(action -> action.getActionStatus().equals("Pending")).toList();
        return ResponseEntity.ok(requests.stream().map(ActionDTO::new).toList());
    }
}
