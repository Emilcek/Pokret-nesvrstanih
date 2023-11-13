package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.StatusRepository;
import com.progi.WildTrack.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepo;
}
