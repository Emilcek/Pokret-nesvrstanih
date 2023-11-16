package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;

import java.util.List;

public interface ClientService {
    List<ClientDetailsDTO> getAllClients();

    ClientDetailsDTO getClientByClientName(String clientName);

    List<ClientDetailsDTO> getAllRequests();

    ClientDetailsDTO updateClient(ClientUpdateDTO client);

    ClientDetailsDTO updateClientByClientName(String clientName, Integer status);

    ClientDetailsDTO getClient();

    void createAdmin();

}
