package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StationId", nullable = false)
    private Long stationId;
    @Column(name = "StationName", length = 50, nullable = false)
    private String stationName;
    @Column(name = "Radius", nullable = false)
    private int radius;
    @Column(name = "StationStatus", nullable = false)
    private String stationStatus;
    @Column(name = "StationLocation", nullable = false)
    private String stationLocation;

    @OneToOne(mappedBy = "station", cascade = CascadeType.ALL)
    private StationLead stationLead;
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Explorer> explorers;


}
