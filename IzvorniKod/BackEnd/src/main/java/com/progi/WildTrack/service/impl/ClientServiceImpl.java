package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ClientRepository;
import com.progi.WildTrack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepo;
}
