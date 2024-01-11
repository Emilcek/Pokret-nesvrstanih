package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.*;
import com.progi.WildTrack.domain.*;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.progi.WildTrack.service.impl.AuthenticationServiceImpl.compressPhoto;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private StationLeadRepository stationLeadRepo;

    @Autowired
    private ResearcherRepository researcherRepo;

    @Autowired
    private ExplorerRepository explorerRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private StationRepository stationRepo;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TokenRepository tokenRepository;

    public ClientServiceImpl() {
    }

    @Override
    @Transactional
    public List<ClientDetailsDTO> getAllClients() {
        List<Client> stationLeads = stationLeadRepo.findAll().stream().filter(stationLead -> stationLead.getStatus().getStatusId().equals(2L)).map(StationLead::getClient).toList();
        List<Client> researchers = researcherRepo.findAll().stream().filter(researcher -> researcher.getStatus().getStatusId().equals(2L)).map(Researcher::getClient).toList();
        List<Client> explorers = new ArrayList<>(explorerRepo.findAll().stream().map(Explorer::getClient).toList());
        explorers.addAll(stationLeads);
        explorers.addAll(researchers);
        return explorers.stream().filter(client -> !client.getRole().equals("admin") && client.isVerified()).map(ClientDetailsDTO::new).toList();
    }

    @Override
    @Transactional
    public ResponseEntity<ClientDetailsDTO> getClientByClientName(String clientName) {
        Client client = clientRepo.findByClientName(clientName).orElse(null);
        if (client == null) {
            System.out.println("Client not found");
            return ResponseEntity.badRequest().build();
        }
        if (!client.isVerified()) {
            System.out.println("Client not verified");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(clientDetailsDTObyRole(client));
    }

    @Override
    @Transactional
    public List<ClientDetailsDTO> getAllRequests() {
        List<StationLead> StationLeads = stationLeadRepo.findAllByStatusStatusId(1L);
        List<Researcher> Researchers = researcherRepo.findAllByStatusStatusId(1L);
        List<Client> requestStationLeads = new ArrayList<>(StationLeads.stream().map(StationLead::getClient).toList());
        List<Client> requestResearchers = Researchers.stream().map(Researcher::getClient).toList();
        requestStationLeads.addAll(requestResearchers);
        return requestStationLeads.stream().filter(Client::isVerified).map(ClientDetailsDTO::new).toList();
    }

    public List<ClientDetailsDTO> getAllAvailableExplorers() {
        List<Explorer> explorers = explorerRepo.findAllByStationIsNull();
        return explorers.stream().map(Explorer::getClient).map(ClientDetailsDTO::new).toList();
    }

    @Override
    @Transactional
    public ResponseEntity updateClient(ClientUpdateDTO client) {
        System.out.println(client);
        Client clientToUpdate = clientRepo.findByClientName(client.getClientName()).orElse(null);
        if (clientToUpdate == null) {
            return ResponseEntity.badRequest().build();
        }
        String oldRole = clientToUpdate.getRole();
        clientToUpdate.setFirstName(client.getFirstName());
        clientToUpdate.setLastName(client.getLastName());
        clientToUpdate.setRole(client.getRole());
        byte[] compressedPhoto = compressPhoto(client.getClientPhoto());
        if (!Arrays.equals(compressedPhoto, clientToUpdate.getClientPhoto())) {
            System.out.println("Photo changed");
            clientToUpdate.setClientPhoto(compressedPhoto);
        }
        System.out.println("old role " + oldRole);
        System.out.println("new role " + client.getRole());
        // slucaj kad je admin promijenio ulogu korisnika, brise se stari objekt i dodaje novi
        if (client.getRole() != null && !client.getRole().equals(oldRole)) {
            List<Token> tokens = tokenRepository.findAllValidTokenByClient(client.getClientName());
            if (!tokens.isEmpty()) {
                tokenRepository.deleteAll(tokens);
            }
            switch (oldRole) {
                case "voditeljPostaje" -> {
                    StationLead stationLead = stationLeadRepo.findByStationLeadName(clientToUpdate.getClientName());
                    stationLeadRepo.delete(stationLead);
                    clientToUpdate.setStationLead(null);
                }
                case "istrazivac" -> {
                    Researcher researcher = researcherRepo.findByResearcherName(clientToUpdate.getClientName());
                    researcherRepo.delete(researcher);
                    clientToUpdate.setResearcher(null);
                }
                case "tragac" -> {
                    Explorer explorer = explorerRepo.findByExplorerName(clientToUpdate.getClientName());
                    explorerRepo.delete(explorer);
                    clientToUpdate.setExplorer(null);
                }
            }
            switch (client.getRole()) {
                case "voditeljPostaje" -> {
                    StationLead stationLead = new StationLead(clientToUpdate, statusRepo.findByStatusId(2));
                    stationLeadRepo.save(stationLead);
                }
                case "istrazivac" -> {
                    Researcher researcher = new Researcher(clientToUpdate, statusRepo.findByStatusId(2));
                    researcherRepo.save(researcher);
                }
                case "tragac" -> {
                    System.out.println("dodavanje tragac");
                    Explorer explorer = new Explorer(clientToUpdate);
                    explorerRepo.save(explorer);
                    for (String i : client.getEducatedFor()) {
                        Vehicle vehicle = (Vehicle) vehicleRepository.findByVehicleType(i).orElseThrow();
                        vehicleService.addExplorerToVehicle(vehicle, explorer);
                    }
                }
            }
            clientRepo.save(clientToUpdate);
        }
//        slucaj kad je admin samo promijenio kompetencije tragaca
        else if (client.getRole().equals("tragac")) {
            Explorer explorer = explorerRepo.findByExplorerName(clientToUpdate.getClientName());
            List<Vehicle> vehiclesToRemove = new ArrayList<>();
            for (Vehicle vehicle : explorer.getVehicles()) {
                if (!client.getEducatedFor().contains(vehicle.getVehicleType())) {
                    vehiclesToRemove.add(vehicle);
                }
            }
            for (Vehicle vehicle : vehiclesToRemove) {
                vehicleService.removeExplorerFromVehicle(vehicle, explorer);
            }
            for (String i : client.getEducatedFor()) {
                Vehicle vehicle = (Vehicle) vehicleRepository.findByVehicleType(i).orElseThrow();
                vehicleService.addExplorerToVehicle(vehicle, explorer);
            }

        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteClient() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Token> tokens = tokenRepository.findAllValidTokenByClient(client.getClientName());
        if (!tokens.isEmpty()) {
            tokenRepository.deleteAll(tokens);
        }
        switch (client.getRole()) {
            case "voditeljPostaje" -> {
                StationLead stationLead = stationLeadRepo.findByStationLeadName(client.getClientName());
                stationLeadRepo.delete(stationLead);
            }
            case "istrazivac" -> {
                Researcher researcher = researcherRepo.findByResearcherName(client.getClientName());
                researcherRepo.delete(researcher);
            }
            case "tragac" -> {
                Explorer explorer = explorerRepo.findByExplorerName(client.getClientName());
                explorerRepo.delete(explorer);
            }
        }
        clientRepo.delete(client);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ClientDetailsDTO updateClientStatusByClientName(String clientName, Integer status) {
        Client client = clientRepo.findByClientName(clientName).orElse(null);
        Status clientStatus = statusRepo.findByStatusId(status);
        if (client == null) {
            return null;
        }
        if (client.getRole().equals("voditeljPostaje")) {
            StationLead stationLead = stationLeadRepo.findByStationLeadName(clientName);
            stationLead.setStatus(clientStatus);
            stationLeadRepo.save(stationLead);
        } else if (client.getRole().equals("istrazivac")) {
            Researcher researcher = researcherRepo.findByResearcherName(clientName);
            researcher.setStatus(clientStatus);
            researcherRepo.save(researcher);
        }
        clientRepo.save(client);
        return new ClientDetailsDTO(client);
    }

    @Override
    public ClientDetailsDTO getClient() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientDetailsDTObyRole(client);
    }

    private ClientDetailsDTO clientDetailsDTObyRole(Client client) {
        ClientDetailsDTO clientDetailsDTO;
        switch (client.getRole()) {
            case "voditeljPostaje" -> {
                clientDetailsDTO = new ClientDetailsDTO(client, client.getStationLead());
            }
            case "tragac" -> {
                clientDetailsDTO = new ClientDetailsDTO(client, client.getExplorer());
            }
            case "istrazivac" -> {
                clientDetailsDTO = new ClientDetailsDTO(client, client.getResearcher());
            }
            default -> clientDetailsDTO = new ClientDetailsDTO(client);
        };
        return clientDetailsDTO;
    }

    @Override
    public void createAdmin() {
        if (clientRepo.findByClientName("admin").isPresent()) {
            return;
        }
        Client admin = Client.builder()
                .clientName("admin")
                .firstName("admin")
                .lastName("admin")
                .clientPassword(passwordEncoder.encode("admin123"))
                .clientPhoto(new byte[0])
                .email("admin@admin")
                .isVerified(true)
                .role("admin")
                .build();
        clientRepo.save(admin);
    }
}
