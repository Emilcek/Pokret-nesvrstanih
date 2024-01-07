package com.progi.WildTrack.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "explorerName")
public class Explorer {
    @Id
    @Column(name = "Explorername" ,length = 30, nullable = false)
    private String explorerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername" ,referencedColumnName = "Clientname")
    @MapsId
    private Client client;

    @JsonManagedReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "StationId")
    private Station station;

    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExplorerAction> explorerActions;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnimalComment> animalComments;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> task;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExplorerLocation> explorerLocations;
    @ManyToMany(mappedBy = "explorers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Vehicle> vehicles = new HashSet<>();

    public Explorer(Client client) {
        this.client = client;
    }

}
