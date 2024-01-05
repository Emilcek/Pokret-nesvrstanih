package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.ClientUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface StationLeadService {

    ResponseEntity updateClient(ClientUpdateDTO client);

}
