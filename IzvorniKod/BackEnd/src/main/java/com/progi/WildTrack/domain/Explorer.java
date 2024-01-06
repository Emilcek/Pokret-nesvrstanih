package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Explorer")
public class Explorer {
    @Id
    @Column(name = "Explorername" ,length = 30, nullable = false)
    private String explorerName;

    @Column(name = "ExplorerStatus", nullable = false)
    private String explorerStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername" ,referencedColumnName = "Clientname")
    @MapsId
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "StationId")
    private Station station;
    @ManyToMany(mappedBy = "explorers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Action> actions = new HashSet<>();
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnimalComment> animalComments;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> tasks;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExplorerLocation> explorerLocations;
    @ManyToMany(mappedBy = "explorers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Vehicle> vehicles = new HashSet<>();

    public Explorer(Client client) {
        this.client = client;
        this.explorerStatus = "Available";
    }

}
