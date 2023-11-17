package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.StatusRepository;
import com.progi.WildTrack.domain.Description;
import com.progi.WildTrack.domain.Status;
import com.progi.WildTrack.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepo;

    @Override
    public void createStatus(Description statusDescription) {
        if (statusRepo.findByDescription(statusDescription).isPresent()) {
            return;
        }
        Status status = Status.builder()
                .description(statusDescription)
                .build();
        statusRepo.save(status);
    }
}
