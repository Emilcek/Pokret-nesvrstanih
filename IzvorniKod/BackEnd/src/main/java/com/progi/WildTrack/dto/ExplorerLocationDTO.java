package com.progi.WildTrack.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExplorerLocationDTO {
        private String longitude;
        private String latitude;
        private String locationTimestamp;
        private String explorerName;
}
