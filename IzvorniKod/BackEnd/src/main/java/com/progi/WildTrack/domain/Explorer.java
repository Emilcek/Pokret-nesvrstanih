package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Explorer")
public class Explorer {
    @Id
    @Column(name = "Explorername" ,length = 30, nullable = false)
    private String explorerName;
    @Column(name = "StationId", nullable = false)
    private int stationId;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "Explorername" ,referencedColumnName = "Clientname")
   @MapsId
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "StationId", insertable = false, updatable = false)
    private Station station;

    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExplorerAction> explorerActions;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnimalComment> animalComments;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> task;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExplorerLocation> explorerLocations;
    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EducatedFor> educatedFors;


}
