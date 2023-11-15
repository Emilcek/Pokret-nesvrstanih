package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
