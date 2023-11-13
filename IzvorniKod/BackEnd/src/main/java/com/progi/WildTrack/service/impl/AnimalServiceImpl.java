package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.AnimalRepository;
import com.progi.WildTrack.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepo;
}
