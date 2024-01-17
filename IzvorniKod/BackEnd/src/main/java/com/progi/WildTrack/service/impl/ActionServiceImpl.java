package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.*;
import com.progi.WildTrack.domain.*;
import com.progi.WildTrack.dto.*;
import com.progi.WildTrack.service.ActionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private AnimalLocationRepository animalLocationRepo;
    @Autowired
    private ResearcherRepository researcherRepo;
    @Autowired
    private ExplorerLocationRepository explorerLocationRepo;
    @Autowired
    private StationLeadRepository stationLeadRepo;

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
        Researcher researcher = researcherRepo.findByResearcherName(client.getUsername());
        Station station = stationRepo.findByStationName(request.getStation());
        StationLead stationLead = stationLeadRepo.findByStationLeadName(station.getStationLead().getStationLeadName());
        if (stationLead == null || researcher == null) {
            return ResponseEntity.badRequest().build();
        }

        Action action = Action.builder()
                .actionName(request.getName())
                .actionDescription(request.getDescription())
                .actionStatus("Pending")
                .researcher(researcher)
                .stationLead(stationLead)
                .build();
        actionRepo.save(action);

        for (TaskDTO task : request.getTasks()) {
            Vehicle vehicle = (Vehicle) vehicleRepo.findByVehicleType(task.getTaskVehicle()).orElseThrow();
            System.out.println("task " + task.getTaskVehicle());
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
            action.getExplorers().forEach(explorer1 -> System.out.println("explorer " + explorer1.getExplorerName()));
            actionRepo.save(action);
        }
        return ResponseEntity.ok().build();
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

    @Transactional
    @Override
    public ResponseEntity getActionAnimalLocations(Long actionId) {
        Action action = actionRepo.findByActionId(actionId);
        if (action == null) {
            return ResponseEntity.badRequest().body("Action not found");
        }
        //check if client is researcher or explorer
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Explorer explorer = client.getExplorer();
        Researcher researcher = client.getResearcher();
        if (explorer == null && researcher == null) {
            return ResponseEntity.badRequest().body("Client not found");
        }
        //check if explorer/researcher is on action
        Set<Explorer> explorers = action.getExplorers();
        if (!explorers.contains(explorer) && !action.getResearcher().equals(researcher)) {
            return ResponseEntity.badRequest().body("Client is not on action");
        }

        var tasks = action.getTasks();
        List<AnimalDetailsDTO> animalDetailsDTOList = new java.util.ArrayList<>();
        tasks.forEach(task -> {
            Animal animal = task.getAnimal();
            if (animal != null) {
                AnimalLocation animalLocation = animalLocationRepo.findFirstByAnimal_AnimalIdOrderByAnimalLocationTSDesc(animal.getAnimalId());
                if (animalLocation != null) {
                    AnimalDetailsDTO animalDetailsDTO = AnimalDetailsDTO.builder()
                            .animalId(animal.getAnimalId())
                            .animalSpecies(animal.getSpecies())
                            //.animalPhotoURL(animal.getAnimalPhotoURL())
                            .animalDescription(animal.getAnimalDescription())
                            .latitude(animalLocation.getLocationofAnimal().split(",")[0])
                            .longitude(animalLocation.getLocationofAnimal().split(",")[1])
                            .build();
                    animalDetailsDTOList.add(animalDetailsDTO);
                }
            }
        });

        return ResponseEntity.ok(animalDetailsDTOList);
    }

    @Override
    public ResponseEntity getActionExplorerLocations(Long actionId) {
        Action action = actionRepo.findByActionId(actionId);
        if (action == null) {
            return ResponseEntity.badRequest().body("Action not found");
        }
        //check if client is researcher or explorer
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Explorer explorer = client.getExplorer();
        Researcher researcher = client.getResearcher();
        if (explorer == null && researcher == null) {
            return ResponseEntity.badRequest().body("Client not found");
        }
        //check if explorer/researcher is on action
        Set<Explorer> explorers = action.getExplorers();
        if (!explorers.contains(explorer) && action.getResearcher() == null) {
            return ResponseEntity.badRequest().body("Client is not on action");
        }
        //for each explorer in explorers find last location
        List<ExplorerDetailsDTO> explorerDetailsDTOList = new java.util.ArrayList<>();
        explorers.forEach(explorer1 -> {
            ExplorerLocation explorerLocation = explorerLocationRepo.findFirstByExplorer_ExplorerNameOrderByLocationTimestampDesc(explorer1.getExplorerName());
            if (explorerLocation != null) {
                ExplorerDetailsDTO explorerDetailsDTO = ExplorerDetailsDTO.builder()
                        .explorerName(explorer1.getExplorerName())
                        .firstName(explorer1.getClient().getFirstName())
                        .lastName(explorer1.getClient().getLastName())
                        .email(explorer1.getClient().getEmail())
                        //.clientPhoto(explorer1.getClient().getClientPhoto())
                        //.status(explorer1.getExplorerStatus())
                        // .stationName(explorer1.getStation().getStationName())
                        .educatedFor(explorer1.getVehicles())
                        .latitude(explorerLocation.getLocationOfExplorer().split(",")[0])
                        .longitude(explorerLocation.getLocationOfExplorer().split(",")[1])
                        .build();
                explorerDetailsDTOList.add(explorerDetailsDTO);
            }
        });

        return ResponseEntity.ok(explorerDetailsDTOList);
    }


}
