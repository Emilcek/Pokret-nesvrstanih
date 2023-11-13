package com.progi.WildTrack.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Researcher")
public class Researcher {
    @Id
    @Column(name = "Researchername" ,length = 30, nullable = false)
    private String researcherName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "StatusId")
    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Resarchername" ,referencedColumnName = "CLientname")
    @MapsId
    private Client client;
    @OneToMany(mappedBy = "researcher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> Requests;
    @OneToMany(mappedBy = "researcher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Action> actions;


    public Researcher() {
    }


}
