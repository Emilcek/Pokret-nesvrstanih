package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.RequestRepository;
import com.progi.WildTrack.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepo;
}
