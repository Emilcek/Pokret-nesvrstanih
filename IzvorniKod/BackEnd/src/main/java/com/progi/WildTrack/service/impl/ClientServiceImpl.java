package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ClientRepository;
import com.progi.WildTrack.dao.ResearcherRepository;
import com.progi.WildTrack.dao.StationLeadRepository;
import com.progi.WildTrack.dao.StatusRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Researcher;
import com.progi.WildTrack.domain.StationLead;
import com.progi.WildTrack.domain.Status;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import com.progi.WildTrack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private StatusRepository statusRepo;

    public ClientServiceImpl() {
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = clientRepo.findAll();
        return clients;
    }

    @Override
    public Client getClientByClientName(String clientName) {
        return clientRepo.findByClientName(clientName).orElse(null);
    }

    @Override
    public List<Client> getAllRequests() {
        List<StationLead> StationLeads = stationLeadRepo.findAllByStatusStatusId(1L);
        List<Researcher> Researchers = researcherRepo.findAllByStatusStatusId(1L);
        List<Client> requestStationLeads = new ArrayList<>(StationLeads.stream().map(StationLead::getClient).toList());
        List<Client> requestResearchers = Researchers.stream().map(Researcher::getClient).toList();
        requestStationLeads.addAll(requestResearchers);
        return requestStationLeads;
    }

    @Override
    public Client updateClient(ClientUpdateDTO client) {
        Client clientToUpdate = clientRepo.findByClientName(client.getClientName()).orElse(null);
        clientToUpdate.setFirstName(client.getFirstName());
        clientToUpdate.setLastName(client.getLastName());
        return clientRepo.save(clientToUpdate);
    }

    @Override
    public Client updateClientByClientName(String clientName, Integer status) {
        Client client = clientRepo.findByClientName(clientName).orElse(null);
        Status clientStatus = statusRepo.findByStatusId(status);
        if (client.getRole().equals("voditeljPostaje")) {
            StationLead stationLead = stationLeadRepo.findByStationLeadName(clientName);
            stationLead.setStatus(clientStatus);
            stationLeadRepo.save(stationLead);
            return clientRepo.save(client);
        } else if (client.getRole().equals("istrazivac")) {
            Researcher researcher = researcherRepo.findByResearcherName(clientName);
            researcher.setStatus(clientStatus);
            researcherRepo.save(researcher);
            return clientRepo.save(client);
        } else {
            return null;
        }

    }

    @Override
    public Client getClient() {
        return (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void createAdmin() {
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

    @Override
    public void save(Client client) {
        clientRepo.save(client);
    }
}
