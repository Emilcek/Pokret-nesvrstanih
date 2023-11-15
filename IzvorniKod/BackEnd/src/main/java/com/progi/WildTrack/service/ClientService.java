package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    Client getClientByClientName(String clientName);

    List<Client> getAllRequests();

    Client updateClient(ClientUpdateDTO client);

    Client updateClientByClientName(String clientName, Integer status);

    Client getClient();

    void createAdmin();

    ClientDetailsDTO getClientDetails();
}
