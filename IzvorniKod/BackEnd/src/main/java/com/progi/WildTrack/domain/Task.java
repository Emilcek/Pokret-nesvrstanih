package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "TaskId", nullable = false)
    private Long taskId;
    @Column(name="TaskDescription",nullable = false)
    private String taskDescription;
    @Column(name="EndLocation")
    private String endLocation;
    @Column(name="StartLocation", nullable = false)
    private String startLocation;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TaskTS", nullable = false)
    private Timestamp taskTS;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ActionId")
    private Action action;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername")
    private Explorer explorer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VehicleId")
    private Vehicle vehicle;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AnimalId")
    private Animal animal;

    public Task() {
    }

}
