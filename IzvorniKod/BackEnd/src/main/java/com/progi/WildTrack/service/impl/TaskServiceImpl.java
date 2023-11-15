package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.TaskRepository;
import com.progi.WildTrack.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepo;
}
