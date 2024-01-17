package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AnimalComment")
public class AnimalComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnimalCommentId", nullable = false)
    private Long animalCommentId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AnimalCommentTS", nullable = false)
    private Timestamp animalCommentTS;

    @Column(name = "AnimalDescription")
    private String animalDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername")
    private Explorer explorer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AnimalId")
    private Animal animal;

}
