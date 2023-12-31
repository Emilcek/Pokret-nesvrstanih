package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.CreateRequestDTO;
import com.progi.WildTrack.dto.ExplorerTaskDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RequestService {
    ResponseEntity createRequest(CreateRequestDTO request);

    ResponseEntity getStationLeadRequests();

    ResponseEntity acceptRequest(Long requestId, List<ExplorerTaskDTO> explorerTasks);

    ResponseEntity declineRequest(Long requestId);

    ResponseEntity getResearcherRequests();
}
