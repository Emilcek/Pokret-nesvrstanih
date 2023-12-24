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

    public String clientName;
    public String firstName;
    public String lastName;
    public String role;
    public MultipartFile clientPhoto;
    public String stationName;
    public List<String> educatedFor;
}
