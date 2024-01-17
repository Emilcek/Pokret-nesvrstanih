package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AnimalCommentDTO;
import org.springframework.http.ResponseEntity;

public interface AnimalCommentService {
    ResponseEntity createAnimalComment(Long animalId, AnimalCommentDTO animalCommentDTO);

    ResponseEntity getAnimalComments(Long animalId);
}
