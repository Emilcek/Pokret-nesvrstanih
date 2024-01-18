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
    public ResponseEntity addExplorerLocation(String explorerName, ExplorerLocationDTO explorerLocationDTO) {
        //check if explorer exists
        if (!explorerRepo.existsByExplorerName(explorerName)) {
            return ResponseEntity.badRequest().body("Explorer not found");
        }
        //if explorer exists -> create new explorer location with timestamp
        ExplorerLocation explorerLocation = ExplorerLocation.builder()
                .explorer(explorerRepo.findById(explorerName).get())
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
        if (explorerLocation == null) {
            return ResponseEntity.badRequest().body("Explorer location not found");
        }
        //var explorer = explorerRepo.findByExplorerName(explorerName);
        ExplorerDetailsDTO explorerDetailsDTO = ExplorerDetailsDTO.builder()
                .explorerName(explorerLocation.getExplorer().getExplorerName())
                .firstName(explorerLocation.getExplorer().getClient().getFirstName())
                .lastName(explorerLocation.getExplorer().getClient().getLastName())
                .email(explorerLocation.getExplorer().getClient().getEmail())
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
        if (explorerLocationList == null) {
            return ResponseEntity.badRequest().body("Explorer locations not found");
        }
        Set<ExplorerLocationDTO> explorerLocationDTOList = new java.util.HashSet<>();
        explorerLocationList.forEach(explorerLocation -> {
            ExplorerLocationDTO explorerLocationDTO = ExplorerLocationDTO.builder()
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
            var explorerLocationList = explorerLocationRepo.findAllByExplorer_ExplorerName(explorer.getExplorerName())
                    .stream()
                    .map(explorerLocation -> ExplorerLocationDTO.builder()
                            .latitude(explorerLocation.getLocationOfExplorer().split(",")[0])
                            .longitude(explorerLocation.getLocationOfExplorer().split(",")[1])
                            .locationTimestamp(explorerLocation.getLocationTimestamp().toString())
                            .build())
                    .collect(java.util.stream.Collectors.toSet());
            if (!explorerLocationList.isEmpty()) {
                ExplorerAllLocationsDTO explorerAllLocationsDTO = ExplorerAllLocationsDTO.builder()
                        .explorerName(explorer.getExplorerName())
                        .firstName(explorer.getClient().getFirstName())
                        .lastName(explorer.getClient().getLastName())
                        .email(explorer.getClient().getEmail())
                        .educatedFor(explorer.getVehicles())
                        .explorerLocations(explorerLocationList)
                        .build();
                explorerAllLocationsDTOList.add(explorerAllLocationsDTO);
            }

        });

        return ResponseEntity.ok(explorerAllLocationsDTOList);
    }

    @Override
    public ResponseEntity getAllExplorersCurrentLocations() {
        var explorers = explorerRepo.findAll();
        List<ExplorerDetailsDTO> explorerDetailsDTOList = new java.util.ArrayList<>();
        explorers.forEach(explorer -> {
            ExplorerLocation explorerLocation = explorerLocationRepo.findFirstByExplorer_ExplorerNameOrderByLocationTimestampDesc(explorer.getExplorerName());
            if (explorerLocation != null) {
                ExplorerDetailsDTO explorerDetailsDTO = ExplorerDetailsDTO.builder()
                        .explorerName(explorerLocation.getExplorer().getExplorerName())
                        .firstName(explorerLocation.getExplorer().getClient().getFirstName())
                        .lastName(explorerLocation.getExplorer().getClient().getLastName())
                        .email(explorerLocation.getExplorer().getClient().getEmail())
                        .educatedFor(explorerLocation.getExplorer().getVehicles())
                        .latitude(explorerLocation.getLocationOfExplorer().split(",")[0])
                        .longitude(explorerLocation.getLocationOfExplorer().split(",")[1])
                        .build();
                explorerDetailsDTOList.add(explorerDetailsDTO);
            }

        });
        return ResponseEntity.ok(explorerDetailsDTOList);
    }


}
