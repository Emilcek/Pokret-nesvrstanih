package com.progi.WildTrack.dto;

import com.progi.WildTrack.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTaskDTO {
    private Long taskId;
    private String taskDescription;
    private String endLocation;
    private String startLocation;
    private String taskStatus;
    private Timestamp taskTS;
    private String actionName;
    private Long actionId;
    private List<TaskCommentDTO> taskComments;
    private String explorerName;
    private String vehicleName;
    private String animalSpecies;

    public ResponseTaskDTO(Task task) {

        this.taskId = task.getTaskId();
        this.taskDescription = task.getTaskDescription();
        this.endLocation = task.getEndLocation();
        this.startLocation = task.getStartLocation();
        this.taskStatus = task.getTaskStatus();
        this.taskTS = task.getTaskTS();
        this.actionName = task.getAction().getActionName();
        this.actionId = task.getAction().getActionId();
        this.taskComments =  task.getTaskComments().stream().map(TaskCommentDTO::new).toList();
        this.explorerName = task.getExplorer().getExplorerName();
        this.vehicleName = task.getVehicle().getVehicleType();
        this.animalSpecies = task.getAnimal().getSpecies();
    }
}


