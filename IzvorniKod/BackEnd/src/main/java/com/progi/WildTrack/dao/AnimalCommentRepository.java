package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.AnimalComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalCommentRepository extends JpaRepository<AnimalComment, Long> {
}
