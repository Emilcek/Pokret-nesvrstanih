package com.progi.WildTrack.domain;

import com.progi.WildTrack.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "TaskId", nullable = false)
    private Long taskId;
    @Column(name="TaskDescription",nullable = false)
    private String taskDescription;
    @Column(name="EndLocation", nullable = false)
    private String endLocation;
    @Column(name="StartLocation")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="RequestId")
    private Request request;


    public Task(TaskDTO task, Vehicle vehicle) {
        this.taskDescription = task.getDescription();
        this.endLocation = task.getEndLocation();
        this.startLocation = task.getStartLocation();
        this.vehicle = vehicle;

    }
}
