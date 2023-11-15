package com.progi.WildTrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetailsDTO {

    private String clientName;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String clientPhotoURL;
}
