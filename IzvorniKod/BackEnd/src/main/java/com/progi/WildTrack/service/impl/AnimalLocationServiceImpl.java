package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.AnimalLocationRepository;
import com.progi.WildTrack.service.AnimalCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalLocationServiceImpl implements AnimalCommentService {
    @Autowired
    private AnimalLocationRepository animalLocationRepo;
}
