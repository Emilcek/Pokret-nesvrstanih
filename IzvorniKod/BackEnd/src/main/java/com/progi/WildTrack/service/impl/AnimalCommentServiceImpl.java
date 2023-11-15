package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.AnimalRepository;
import com.progi.WildTrack.service.AnimalCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalCommentServiceImpl implements AnimalCommentService {
    @Autowired
    private AnimalRepository animalRepo;
}
