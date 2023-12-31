package com.progi.WildTrack.service;

import org.springframework.http.ResponseEntity;

public interface ActionService {

    ResponseEntity getActions();

    ResponseEntity getAction(Long actionId);
}
