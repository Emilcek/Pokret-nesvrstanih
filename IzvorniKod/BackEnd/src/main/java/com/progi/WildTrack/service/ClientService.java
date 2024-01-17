package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.ClientUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

public interface ClientService {
    List<ClientDetailsDTO> getAllClients();

    ResponseEntity<ClientDetailsDTO> getClientByClientName(String clientName);

    List<ClientDetailsDTO> getAllAvailableExplorers();

    List<ClientDetailsDTO> getAllRequests();

    ResponseEntity updateClient(ClientUpdateDTO client);

    ResponseEntity deleteClient();

    ResponseEntity deleteClient(String clientName);

    ClientDetailsDTO updateClientStatusByClientName(String clientName, Integer status);

    ClientDetailsDTO getClient();

    void createAdmin();

}
