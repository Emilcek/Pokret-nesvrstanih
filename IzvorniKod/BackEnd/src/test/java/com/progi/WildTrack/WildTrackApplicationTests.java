package com.progi.WildTrack;

import com.progi.WildTrack.dao.ClientRepository;
import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.dao.StationRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.domain.Vehicle;
import com.progi.WildTrack.dto.RegisterDto;
import com.progi.WildTrack.service.AuthenticationService;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.StationLeadService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WildTrackApplicationTests {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private StationLeadService stationLeadService;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ExplorerRepository explorerRepository;


    @Test
    @DisplayName("Test addStationLeadToStation")
    public void addStationLeadToStation() {
        MultipartFile multipartFile = new MockMultipartFile("clientPhoto", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        RegisterDto stationLead = new RegisterDto("stationLead", "test", "test",
                "stationLead@test.test", "test", "voditeljPostaje", null, multipartFile);
        authenticationService.register(stationLead);
        Station station = new Station (1L, "Postaja test", 2, "Inactive",
                "45.568000, 17.626000", null, new ArrayList<>());
        stationRepository.save(station);
        assertTrue(stationLeadService.assignStationToStationLeadByName("stationLead", "Postaja test"));
        clientService.deleteClient("stationLead");
    }

    @Test
    @DisplayName("Test addStationLeadToStationFail")
    public void addStationLeadToStationFail() {
        MultipartFile multipartFile = new MockMultipartFile("clientPhoto", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        RegisterDto stationLead = new RegisterDto("stationLead", "test", "test",
                "stationLead@test.test", "test", "voditeljPostaje", null, multipartFile);
        authenticationService.register(stationLead);
        assertFalse(stationLeadService.assignStationToStationLeadByName("stationLead", "Nepostojeca postaja"));
        clientService.deleteClient("stationLead");
    }

    @Test
    @DisplayName("Test addVehiclesToExplorer")
    public void addVehiclesToExplorer() {
        List<String> educatedFor = List.of("dron", "helikopter", "hodanje");
        MultipartFile multipartFile = new MockMultipartFile("clientPhoto", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        RegisterDto explorer = new RegisterDto("explorer", "test", "test",
                "explorer@test.test", "test", "tragac", educatedFor, multipartFile);
        authenticationService.register(explorer);
        Explorer explorer2 = explorerRepository.findByExplorerName("explorer");
        List<String> explorerVehicles = new ArrayList<>();
        for (Vehicle vehicle : explorer2.getVehicles()) {
            explorerVehicles.add(vehicle.getVehicleType());
        }
        Collections.sort(explorerVehicles);
        assertEquals(explorerVehicles, educatedFor);
        explorer2.setVehicles(null);
        explorerRepository.save(explorer2);
        clientService.deleteClient("explorer");
    }

    @Test
    @DisplayName("Test addExplorerToStation")
    public void addExplorerToStation() {
        List<String> educatedFor = List.of("dron");
        MultipartFile multipartFile = new MockMultipartFile("clientPhoto", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        RegisterDto explorer = new RegisterDto("explorer", "test", "test",
                "explorer@test.test", "test", "tragac", educatedFor, multipartFile);
        authenticationService.register(explorer);
        Station station = new Station (1L, "Postaja test", 2, "Inactive",
                "45.568000, 17.626000", null, new ArrayList<>());
        stationRepository.save(station);
        stationLeadService.assignExplorerToStation("Postaja test", "explorer");
        Station station2 = stationRepository.findByStationName("Postaja test");
        assertFalse(station2.getExplorers().isEmpty());
    }

    @Test
    @DisplayName("Test removeExplorerFromStation")
    public void removeExplorerFromStation() {
//      Mora se izvoditi nakon addExplorerToStation
        stationLeadService.removeExplorerFromStation("Postaja test", "explorer");
        Station station2 = stationRepository.findByStationName("Postaja test");
        assertTrue(station2.getExplorers().isEmpty());
        Explorer explorer2 = explorerRepository.findByExplorerName("explorer");
        explorer2.setStation(null);
        explorer2.setVehicles(null);
        station2.setExplorers(null);
        stationRepository.save(station2);
        explorerRepository.save(explorer2);
        clientService.deleteClient("explorer");
        stationRepository.delete(station2);
    }

    @Test
    @DisplayName("Test acceptClient")
    public void acceptClient() {
        MultipartFile multipartFile = new MockMultipartFile("clientPhoto", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        RegisterDto researcher = new RegisterDto("researcher", "test", "test",
                "researcher@test.test", "test", "istrazivac", null, multipartFile);
        authenticationService.register(researcher);
        clientService.updateClientStatusByClientName("researcher", 2);
        Client client = clientRepository.findByClientName("researcher").orElseThrow();
        assertEquals(2, client.getResearcher().getStatus().getStatusId());
        clientService.deleteClient("researcher");
    }

    @Test
    @DisplayName("Test rejectClient")
    public void rejectClient() {
        MultipartFile multipartFile = new MockMultipartFile("clientPhoto", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        RegisterDto researcher = new RegisterDto("researcher", "test", "test",
                "researcher@test.test", "test", "istrazivac", null, multipartFile);
        authenticationService.register(researcher);
        clientService.updateClientStatusByClientName("researcher", 3);
        Client client = clientRepository.findByClientName("researcher").orElseThrow();
        assertEquals(3, client.getResearcher().getStatus().getStatusId());
        clientService.deleteClient("researcher");
    }

    @Test
    @DisplayName("Test adminAvailable")
    public void adminAvailable(){
        assertTrue(clientRepository.existsByClientName("admin"));
    }

}
