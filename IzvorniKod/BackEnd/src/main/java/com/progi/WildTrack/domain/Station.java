package com.progi.WildTrack.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "StationName", length = 50, unique = true)
    private String stationName;
    @Column(name = "Radius", nullable = false)
    private int radius;
    @Column(name = "StationStatus", nullable = false)
    private String stationStatus;
    @Column(name = "StationLocation", nullable = false)
    private String stationLocation;

    @JsonBackReference
    @OneToOne(mappedBy = "station", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private StationLead stationLead;

    @OneToMany(mappedBy = "station", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Explorer> explorers;


}
