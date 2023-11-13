package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.service.ExplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExplorerServiceImpl implements ExplorerService {
    @Autowired
    private ExplorerRepository explorerRepo;
}
