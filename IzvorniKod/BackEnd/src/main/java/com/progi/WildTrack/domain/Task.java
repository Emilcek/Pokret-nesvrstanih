package com.progi.WildTrack.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.progi.WildTrack.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Task")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "taskId")
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
    @Column(name="TaskStatus", nullable = false)
    private String taskStatus;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TaskTS", nullable = false)
    private Timestamp taskTS;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ActionId")
    private Action action;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskComment> taskComments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername")
    private Explorer explorer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VehicleId")
    private Vehicle vehicle;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AnimalId")
    private Animal animal;

    public Task(TaskDTO task, Vehicle vehicle, Action action) {
        this.taskDescription = task.getDescription();
        this.endLocation = task.getEndLocation();
        this.startLocation = task.getStartLocation();
        this.vehicle = vehicle;
        this.taskStatus = "Ongoing";
        this.action = action;

    }
}
