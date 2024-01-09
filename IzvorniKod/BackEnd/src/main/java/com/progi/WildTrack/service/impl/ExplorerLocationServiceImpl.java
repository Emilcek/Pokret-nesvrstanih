package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ExplorerLocationRepository;
import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.ExplorerLocation;
import com.progi.WildTrack.dto.ExplorerAllLocationsDTO;
import com.progi.WildTrack.dto.ExplorerDetailsDTO;
import com.progi.WildTrack.dto.ExplorerLocationDTO;
import com.progi.WildTrack.service.ExplorerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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
        return ResponseEntity.ok(explorerLocationDTO);
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

    @Override
    public ResponseEntity getExplorerLocations(String explorerName) {
        Explorer explorer = explorerRepo.findByExplorerName(explorerName);
        if (explorer == null) {
            return ResponseEntity.badRequest().body("Explorer not found");
        }
        Set<ExplorerLocation> explorerLocationList = explorerLocationRepo.findAllByExplorer_ExplorerName(explorerName);
        Set<ExplorerLocationDTO> explorerLocationDTOList = new java.util.HashSet<>();
        explorerLocationList.forEach(explorerLocation -> {
            ExplorerLocationDTO explorerLocationDTO = ExplorerLocationDTO.builder()
                    .explorerName(explorerLocation.getExplorer().getExplorerName())
                    .latitude(explorerLocation.getLocationOfExplorer().split(",")[0])
                    .longitude(explorerLocation.getLocationOfExplorer().split(",")[1])
                    .locationTimestamp(explorerLocation.getLocationTimestamp().toString())
                    .build();
            explorerLocationDTOList.add(explorerLocationDTO);
        });
        return ResponseEntity.ok(explorerLocationDTOList);
    }

    @Override
    public ResponseEntity getAllExplorersAllLocations() {
        var explorers = explorerRepo.findAll();
        List<ExplorerAllLocationsDTO> explorerAllLocationsDTOList = new java.util.ArrayList<>();
        explorers.forEach(explorer -> {
            ExplorerAllLocationsDTO explorerAllLocationsDTO = ExplorerAllLocationsDTO.builder()
                    .explorerName(explorer.getExplorerName())
                    .firstName(explorer.getClient().getFirstName())
                    .lastName(explorer.getClient().getLastName())
                    .email(explorer.getClient().getEmail())
                    .clientPhoto(explorer.getClient().getClientPhoto())
                    .status(explorer.getExplorerStatus())
                    .stationName(explorer.getStation().getStationName())
                    .educatedFor(explorer.getVehicles())
                    .explorerLocations(explorerLocationRepo.findAllByExplorer_ExplorerName(explorer.getExplorerName()))
                    .build();
            explorerAllLocationsDTOList.add(explorerAllLocationsDTO);
        });

        return ResponseEntity.ok(explorerAllLocationsDTOList);
    }


}
