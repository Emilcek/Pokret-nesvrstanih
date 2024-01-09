package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AnimalLocationDTO;
import com.progi.WildTrack.dto.CreateRequestDTO;
import com.progi.WildTrack.dto.ExplorerTaskDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ActionService {

    ResponseEntity getActions();

    ResponseEntity getAction(Long actionId);

    ResponseEntity createRequest(CreateRequestDTO request);

    ResponseEntity acceptRequest(Long requestId, List<ExplorerTaskDTO> explorerTaskDTO);

    ResponseEntity getResearcherRequests();

    ResponseEntity declineRequest(Long requestId);

    ResponseEntity getStationLeadRequests();

    ResponseEntity getActionAnimalLocations(Long actionId, String clientName);


    ResponseEntity getActionExplorerLocations(Long actionId, String clientName);
}
