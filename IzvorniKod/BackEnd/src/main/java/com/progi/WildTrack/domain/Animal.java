package com.progi.WildTrack.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
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
    private List<AnimalComment> animalCommentList;
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnimalLocation> animalLocationList;
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> taskList;

    public Animal() {
    }

}
