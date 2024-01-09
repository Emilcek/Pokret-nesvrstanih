package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ExplorerLocationRepository;
import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.domain.ExplorerLocation;
import com.progi.WildTrack.dto.ExplorerDetailsDTO;
import com.progi.WildTrack.dto.ExplorerLocationDTO;
import com.progi.WildTrack.service.ExplorerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ExplorerLocationServiceImpl implements ExplorerLocationService {
    @Autowired
    private ExplorerLocationRepository explorerLocationRepo;
    @Autowired
    private ExplorerRepository explorerRepo;

    @Override
    public ResponseEntity saveExplorerLocation(ExplorerLocationDTO explorerLocationDTO) {
        //check if explorer exists
       if (!explorerRepo.existsByExplorerName(explorerLocationDTO.getExplorerName())) {
            return ResponseEntity.badRequest().body("Explorer not found");
        }
        //if explorer exists -> create new explorer location with timestamp
        ExplorerLocation explorerLocation = ExplorerLocation.builder()
                .explorer(explorerRepo.findById(explorerLocationDTO.getExplorerName()).get())
                .locationOfExplorer(explorerLocationDTO.getLatitude() + "," + explorerLocationDTO.getLongitude())
                .locationTimestamp(Timestamp.valueOf(explorerLocationDTO.getLocationTimestamp()))
                .build();
        explorerLocationRepo.save(explorerLocation);
        return ResponseEntity.ok("Explorer location added " + explorerLocation);
    }

    @Override
    public ResponseEntity getExplorerLocation(String explorerName) {
        //check if explorer exists
        if (!explorerRepo.existsById(explorerName)) {
            return ResponseEntity.badRequest().body("Explorer not found");
        }
        //if explorer exists -> get last location of explorer
        ExplorerLocation explorerLocation = explorerLocationRepo.findFirstByExplorer_ExplorerNameOrderByLocationTimestampDesc(explorerName);
        //var explorer = explorerRepo.findByExplorerName(explorerName);
        ExplorerDetailsDTO explorerDetailsDTO = ExplorerDetailsDTO.builder()
                .explorerName(explorerLocation.getExplorer().getExplorerName())
                .firstName(explorerLocation.getExplorer().getClient().getFirstName())
                .lastName(explorerLocation.getExplorer().getClient().getLastName())
                .email(explorerLocation.getExplorer().getClient().getEmail())
                .clientPhoto(explorerLocation.getExplorer().getClient().getClientPhoto())
                .status(explorerLocation.getExplorer().getExplorerStatus())
                .stationName(explorerLocation.getExplorer().getStation().getStationName())
                .educatedFor(explorerLocation.getExplorer().getVehicles())
                .latitude(explorerLocation.getLocationOfExplorer().split(",")[0])
                .longitude(explorerLocation.getLocationOfExplorer().split(",")[1])
                .build();
        return ResponseEntity.ok(explorerDetailsDTO);
    }


}
