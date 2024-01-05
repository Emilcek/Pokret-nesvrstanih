package com.progi.WildTrack.dto;

import com.progi.WildTrack.domain.TaskComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskCommentDTO {

    private Timestamp commentTS;
    private String content;
    private String clientName;
    private Long taskId;

    public TaskCommentDTO(TaskComment taskComment) {
            this.commentTS = taskComment.getTaskCommentTS();
            this.content = taskComment.getContent();
            this.clientName = taskComment.getClient().getClientName();
            this.taskId = taskComment.getTask().getTaskId();
    }
}
