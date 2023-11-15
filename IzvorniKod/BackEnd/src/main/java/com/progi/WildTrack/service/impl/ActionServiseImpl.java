package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ActionRepository;
import com.progi.WildTrack.service.ActionServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiseImpl implements ActionServise {
    @Autowired
    private ActionRepository actionRepo;
}
