package com.progi.WildTrack.controllers;


import com.progi.WildTrack.dto.AnimalCommentDTO;
import com.progi.WildTrack.service.AnimalCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animalcomment")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class AnimalCommentController {
    private final AnimalCommentService animalCommentService;

    @PostMapping("/create/{animalId}")
    public ResponseEntity createAnimalComment(@PathVariable Long animalId, @RequestBody AnimalCommentDTO animalCommentDTO){
        return animalCommentService.createAnimalComment(animalId, animalCommentDTO);
    }

    @GetMapping("/get/{animalId}")
    public ResponseEntity getAnimalComments(@PathVariable Long animalId){
        return animalCommentService.getAnimalComments(animalId);
    }
}
