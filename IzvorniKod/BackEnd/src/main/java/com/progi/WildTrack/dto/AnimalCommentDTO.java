package com.progi.WildTrack.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalCommentDTO {
    private String commentTimestamp;
    private String animalComment;
    private String explorerName;
}
