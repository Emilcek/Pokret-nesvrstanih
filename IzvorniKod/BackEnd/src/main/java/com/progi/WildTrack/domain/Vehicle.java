package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Vehicle")
public class Vehicle {
    @Id
    @Column(name = "VehicleId", nullable = false)
    private Long vehicleId;
    @Column(name="VechicleType",length = 30, nullable = false)
    private String vechicleType;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EducatedFor> educatedForList;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> taskList;


    public Vehicle() {
    }


}
