package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.ActionCommentRepository;
import com.progi.WildTrack.service.ActionCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionCommentServiceImpl implements ActionCommentService {
    @Autowired
    private ActionCommentRepository actionCommentRepo;
}
