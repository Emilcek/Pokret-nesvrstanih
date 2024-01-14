package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByTaskId(Long taskId);

    List<Task> findAllByExplorerAndTaskStatus(Explorer explorer, String ongoing);
}
