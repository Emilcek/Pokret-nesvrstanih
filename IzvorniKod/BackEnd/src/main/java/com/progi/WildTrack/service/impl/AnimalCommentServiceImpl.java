package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.AnimalCommentRepository;
import com.progi.WildTrack.dao.AnimalRepository;
import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.domain.Animal;
import com.progi.WildTrack.domain.AnimalComment;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.dto.AnimalCommentDTO;
import com.progi.WildTrack.dto.AnimalDTO;
import com.progi.WildTrack.service.AnimalCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AnimalCommentServiceImpl implements AnimalCommentService {
    @Autowired
    private AnimalRepository animalRepo;
    @Autowired
    private AnimalCommentRepository animalCommentRepo;
    @Autowired
    private ExplorerRepository explorerRepo;

    @Override
    public ResponseEntity createAnimalComment(Long animalId, AnimalCommentDTO animalCommentDTO) {
        if (!animalRepo.existsById(animalId)) {
            return ResponseEntity.badRequest().body("Animal not found");
        }

        AnimalComment animalComment = AnimalComment.builder()
                .animal(animalRepo.findById(animalId).get())
                .animalDescription(animalCommentDTO.getAnimalComment())
                .explorer(explorerRepo.findByExplorerName(animalCommentDTO.getExplorerName()))
                .animalCommentTS(Timestamp.valueOf(animalCommentDTO.getCommentTimestamp()))
                .animal(animalRepo.findById(animalId).get())
                .build();
        animalCommentRepo.save(animalComment);
        return ResponseEntity.ok().body(animalCommentDTO);
    }

    @Override
    public ResponseEntity getAnimalComments(Long animalId) {
        //find animal by id
        if (!animalRepo.existsById(animalId)) {
          return ResponseEntity.badRequest().body("Animal not found");
        }
        //find all animal comments by animal id
        //map animal comments to animal comment DTO
        var animalComments = animalCommentRepo.findAllByAnimalAnimalId(animalId);
        var animalCommentDTOList = animalComments.stream().map(animalComment -> AnimalCommentDTO.builder()
                .animalComment(animalComment.getAnimalDescription())
                .commentTimestamp(animalComment.getAnimalCommentTS().toString())
                .explorerName(animalComment.getExplorer().getExplorerName())
                .build()).toList();
        return ResponseEntity.ok().body(animalCommentDTOList);
    }
}
