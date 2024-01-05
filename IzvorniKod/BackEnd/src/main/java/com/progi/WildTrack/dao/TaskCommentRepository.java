package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Task;
import com.progi.WildTrack.domain.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskComment> findAllByTask(Task task);
}
