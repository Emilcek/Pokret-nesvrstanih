package com.progi.WildTrack.domain;

import jakarta.persistence.*;
@Entity

@Table(name = "EducatedFor")
public class EducatedFor {
    @EmbeddedId
    private EducatedForId educatedForId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername")
    @MapsId
    private Explorer explorer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VehicleId", columnDefinition = "bigint")
    @MapsId
    private Vehicle vehicle;

    public EducatedFor() {
    }


}
