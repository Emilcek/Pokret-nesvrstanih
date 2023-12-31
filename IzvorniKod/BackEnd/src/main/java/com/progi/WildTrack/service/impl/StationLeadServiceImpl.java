package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.RequestRepository;
import com.progi.WildTrack.dao.StationLeadRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.StationLead;
import com.progi.WildTrack.service.StationLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StationLeadServiceImpl implements StationLeadService {
    @Autowired
    private StationLeadRepository stationLeadRepo;

}
