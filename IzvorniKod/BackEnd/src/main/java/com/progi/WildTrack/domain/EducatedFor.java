package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EducatedFor")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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



}
