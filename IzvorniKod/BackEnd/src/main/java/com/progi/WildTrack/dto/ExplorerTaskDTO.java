package com.progi.WildTrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExplorerTaskDTO {

        private String explorerName;
        private Long taskId;
        private String vehicle;
}
