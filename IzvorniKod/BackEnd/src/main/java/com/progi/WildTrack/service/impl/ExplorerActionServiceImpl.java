package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ExplorerActionRepositroy;
import com.progi.WildTrack.service.ExplorerActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExplorerActionServiceImpl implements ExplorerActionService {
    @Autowired
    private ExplorerActionRepositroy explorerActionRepo;
}
