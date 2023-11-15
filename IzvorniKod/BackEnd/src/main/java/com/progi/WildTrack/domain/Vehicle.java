package com.progi.WildTrack.domain;

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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Vehicle_Explorer",
            joinColumns = { @JoinColumn(name = "VehicleId") },
            inverseJoinColumns = { @JoinColumn(name = "Explorername") }
    )
    private Set<Explorer> explorers = new HashSet<>();
//    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<EducatedFor> educatedForList;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> taskList;
}
