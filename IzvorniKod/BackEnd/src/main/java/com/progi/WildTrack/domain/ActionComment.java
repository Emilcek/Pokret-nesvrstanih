package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "ActionComment")
public class ActionComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActionCommentId", nullable = false)
    private Long actionCommentId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ActionCommentTS", nullable = false)
    private Timestamp actionCommentTS;

    @Column(name = "ActionDescription")
    private String actionDescription;

    @Column(name = "AnimalLocation")
    private String animalLocation;


    @Column(name = "ActionId", nullable = false)
    private int actionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Clientname")
    private Client client;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="Action")
    private Action action;


    public ActionComment() {
    }


}
