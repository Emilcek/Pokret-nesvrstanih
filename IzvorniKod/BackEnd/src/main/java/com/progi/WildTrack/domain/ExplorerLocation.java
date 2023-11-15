package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "ExplorerLocation")
public class ExplorerLocation {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LocationTS", nullable = false)
    private Timestamp locationTimestamp;

    @Id
    @Column(name = "LocationofExplorer", nullable = false)
    private String locationOfExplorer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername")
    private Explorer explorer;

    public ExplorerLocation() {
    }
}