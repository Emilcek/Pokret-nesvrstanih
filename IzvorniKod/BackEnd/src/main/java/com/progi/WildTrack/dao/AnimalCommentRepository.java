package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.AnimalComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalCommentRepository extends JpaRepository<AnimalComment, Long> {
    List<AnimalComment> findAllByAnimalAnimalId(Long animalId);
}
