package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Object> findByTaskId(Long taskId);
}
