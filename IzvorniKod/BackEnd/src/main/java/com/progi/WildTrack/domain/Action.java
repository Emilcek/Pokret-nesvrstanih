package com.progi.WildTrack.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActionId", nullable = false)
    private Long actionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Researchername")
    private Researcher researcher;
    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActionComment> actionComments;
    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExplorerAction> explorerActions;
    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    public Action() {
    }

}

