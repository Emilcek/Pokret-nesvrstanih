package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.EducatedForRepository;
import com.progi.WildTrack.service.EducatedForService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducatedForServiceImpl implements EducatedForService {
    @Autowired
    private EducatedForRepository educatedForRepo;
}
