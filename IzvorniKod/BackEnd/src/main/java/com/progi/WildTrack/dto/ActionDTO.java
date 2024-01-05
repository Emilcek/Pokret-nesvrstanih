package com.progi.WildTrack.dto;

import com.progi.WildTrack.domain.Action;
import com.progi.WildTrack.domain.Explorer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionDTO {
    private Long actionId;
    private String actionName;
    private String actionDescription;
    private String actionStatus;
    private String actionStationLeadName;
    private String actionResearcherName;
    List<String> actionExplorers;
    List<ResponseTaskDTO> actionTasks;

    public ActionDTO(Action action) {
        this.actionId = action.getActionId();
        this.actionName = action.getActionName();
        this.actionDescription = action.getActionDescription();
        this.actionStatus = action.getActionStatus();
        this.actionStationLeadName = action.getStationLead().getStationLeadName();
        this.actionResearcherName = action.getResearcher().getResearcherName();
        this.actionExplorers = action.getExplorers().stream().map(Explorer::getExplorerName).toList();
        this.actionTasks = action.getTasks().stream().map(ResponseTaskDTO::new).toList();
    }
}
