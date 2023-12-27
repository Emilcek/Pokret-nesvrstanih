package com.progi.WildTrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDTO {

    private String clientName;
    private String firstName;
    private String lastName;
    private String role;
    private MultipartFile clientPhoto;
    private String stationName;
    private List<String> educatedFor;
}
