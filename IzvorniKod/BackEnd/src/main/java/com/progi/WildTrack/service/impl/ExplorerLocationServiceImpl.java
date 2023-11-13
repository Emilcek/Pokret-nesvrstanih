package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ExplorerLocationRepository;
import com.progi.WildTrack.service.ExplorerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExplorerLocationServiceImpl implements ExplorerLocationService {
    @Autowired
    private ExplorerLocationRepository explorerLocationRepo;
}
