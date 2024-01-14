package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnimalId", nullable = false)
    private Long animalId;
    @Column(name="Species", length = 60, nullable = false)
    private String species;
    @Column(name="AnimalDescription", length = 100, nullable = false)
    private String animalDescription;
    @Column(name= "AnimalPhotoURL", length = 100, nullable = false)
    private String animalPhotoURL;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<AnimalComment> animalCommentList;
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<AnimalLocation> animalLocationList;
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Task> taskList;




}
