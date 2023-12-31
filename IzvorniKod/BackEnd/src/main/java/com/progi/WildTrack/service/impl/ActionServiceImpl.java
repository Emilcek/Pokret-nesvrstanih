package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ActionRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Researcher;
import com.progi.WildTrack.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ActionRepository actionRepo;

    @Override
    public ResponseEntity getActions() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Researcher researcher = client.getResearcher();
        return ResponseEntity.ok(actionRepo.findAllByResearcher(researcher));
    }

    @Override
    public ResponseEntity getAction(Long actionId) {
        return ResponseEntity.ok(actionRepo.findById(actionId));
    }
}
