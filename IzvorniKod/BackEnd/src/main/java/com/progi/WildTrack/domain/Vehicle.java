package com.progi.WildTrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Vehicle")
public class Vehicle {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VehicleId", nullable = false)
    private Long vehicleId;
    @Column(name="VehicleType",length = 30, nullable = false)
    private String vehicleType;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Vehicle_Explorer",
            joinColumns = { @JoinColumn(name = "VehicleId") },
            inverseJoinColumns = { @JoinColumn(name = "Explorername") }
    )
    @JsonIgnore
    private Set<Explorer> explorers = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> taskList;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vehicleType == null) ? 0 : vehicleType.hashCode());
        result = prime * result + ((vehicleId == null) ? 0 : vehicleId.hashCode());
        return result;
    }
}
