package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.*;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Researcher;
import com.progi.WildTrack.domain.StationLead;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Status;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

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

    public ClientServiceImpl() {
    }

    @Override
    public List<ClientDetailsDTO> getAllClients() {
        List<Client> stationLeads = stationLeadRepo.findAll().stream().filter(stationLead -> stationLead.getStatus().getStatusId().equals(2L)).map(StationLead::getClient).toList();
        List<Client> researchers = researcherRepo.findAll().stream().filter(researcher -> researcher.getStatus().getStatusId().equals(2L)).map(Researcher::getClient).toList();
        List<Client> explorers = new ArrayList<>(explorerRepo.findAll().stream().map(Explorer::getClient).toList());
        explorers.addAll(stationLeads);
        explorers.addAll(researchers);
        return explorers.stream().filter(client -> !client.getRole().equals("admin") && client.isVerified()).map(ClientDetailsDTO::new).toList();
    }

    @Override
    public ClientDetailsDTO getClientByClientName(String clientName) {
        Client client = clientRepo.findByClientName(clientName).orElse(null);
        if (client == null) {
            // TODO error handling
            return null;
        }
        if (!client.isVerified()) {
            throw new RuntimeException("Client is not verified");
        }
        return new ClientDetailsDTO(client);
    }

    @Override
    public List<ClientDetailsDTO> getAllRequests() {
        List<StationLead> StationLeads = stationLeadRepo.findAllByStatusStatusId(1L);
        List<Researcher> Researchers = researcherRepo.findAllByStatusStatusId(1L);
        List<Client> requestStationLeads = new ArrayList<>(StationLeads.stream().map(StationLead::getClient).toList());
        List<Client> requestResearchers = Researchers.stream().map(Researcher::getClient).toList();
        requestStationLeads.addAll(requestResearchers);
        return requestStationLeads.stream().filter(Client::isVerified).map(ClientDetailsDTO::new).toList();
    }

    @Override
    public ClientDetailsDTO updateClient(ClientUpdateDTO client) {
        Client clientToUpdate = clientRepo.findByClientName(client.getClientName()).orElse(null);
        if (clientToUpdate == null) {
            // TODO error handling
            return null;
        }
        clientToUpdate.setFirstName(client.getFirstName());
        clientToUpdate.setLastName(client.getLastName());
        clientRepo.save(clientToUpdate);
        return new ClientDetailsDTO(clientToUpdate);
    }

    @Override
    public ClientDetailsDTO updateClientByClientName(String clientName, Integer status) {
        Client client = clientRepo.findByClientName(clientName).orElse(null);
        Status clientStatus = statusRepo.findByStatusId(status);
        if (client == null) {
            // TODO error handling
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
        return new ClientDetailsDTO(client);
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
                .clientPhotoURL("")
                .email("admin@admin")
                .isVerified(true)
                .role("admin")
                .build();
        clientRepo.save(admin);
    }
}
