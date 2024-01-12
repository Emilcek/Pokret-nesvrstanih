package com.progi.WildTrack.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "stationId")
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


    @OneToOne(mappedBy = "station", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private StationLead stationLead;

    @OneToMany(mappedBy = "station", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Explorer> explorers;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stationId == null) ? 0 : stationId.hashCode());
        result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
        result = prime * result + radius;
        result = prime * result + ((stationStatus == null) ? 0 : stationStatus.hashCode());
        result = prime * result + ((stationLocation == null) ? 0 : stationLocation.hashCode());
        return result;
    }
}
