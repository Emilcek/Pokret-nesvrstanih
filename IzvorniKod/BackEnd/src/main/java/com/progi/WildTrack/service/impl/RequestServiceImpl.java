package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.*;
import com.progi.WildTrack.domain.*;
import com.progi.WildTrack.dto.CreateRequestDTO;
import com.progi.WildTrack.dto.ExplorerTaskDTO;
import com.progi.WildTrack.dto.TaskDTO;
import com.progi.WildTrack.service.ActionService;
import com.progi.WildTrack.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepo;
    @Autowired
    private StationLeadRepository stationLeadRepo;
    @Autowired
    private StationRepository stationRepo;
    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private ActionRepository actionRepo;
    @Autowired
    private ExplorerRepository explorerRepo;

    @Override
    public ResponseEntity createRequest(CreateRequestDTO request) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Researcher researcher = client.getResearcher();
        System.out.println(request.getStation());
        Station station = stationRepo.findByStationName(request.getStation());
        StationLead stationLead = stationLeadRepo.findByStation(station);
        Request request1 = Request.builder()
                .requestStatus("Pending")
                .researcher(researcher)
                .stationLead(stationLead)
                .build();
        requestRepo.save(request1);
        for(TaskDTO task : request.getTasks()) {
            Vehicle vehicle = (Vehicle) vehicleRepo.findByVehicleType(task.getTaskVehicle()).orElseThrow();
            Task build = new Task(task, vehicle);
            build.setRequest(request1);
            taskRepo.save(build);
        }
        return null;
    }

    @Override
    public ResponseEntity getStationLeadRequests() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StationLead stationLead = client.getStationLead();
        return ResponseEntity.ok(requestRepo.findAllByStationLead(stationLead));
    }

    @Override
    public ResponseEntity acceptRequest(Long requestId, List<ExplorerTaskDTO> explorerTasks) {
        Request request = requestRepo.findByRequestId(requestId);
        request.setRequestStatus("Accepted");
        requestRepo.save(request);
        Action action = Action.builder()
                .researcher(request.getResearcher())
                .build();
        actionRepo.save(action);
        for (ExplorerTaskDTO explorerTask : explorerTasks) {
            Explorer explorer = explorerRepo.findByExplorerName(explorerTask.getExplorerName());
            explorer.setExplorerStatus("Unavailable");
            explorer.getActions().add(action);
            explorerRepo.save(explorer);

            Vehicle vehicle = (Vehicle) vehicleRepo.findByVehicleType(explorerTask.getVehicle()).orElseThrow();
            Task task = (Task) taskRepo.findByTaskId(explorerTask.getTaskId()).orElseThrow();
            task.setExplorer(explorer);
            task.setAction(action);
            task.setVehicle(vehicle);
            taskRepo.save(task);

            action.getExplorers().add(explorer);
            actionRepo.save(action);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity declineRequest(Long requestId) {
        Request request = requestRepo.findByRequestId(requestId);
        request.setRequestStatus("Declined");
        requestRepo.save(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity getResearcherRequests() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Researcher researcher = client.getResearcher();
        List<Request> requests = requestRepo.findAllByResearcher(researcher);
        return ResponseEntity.ok(requests);
    }
}
