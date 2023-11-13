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
public class RegisterDto {


    private String clientName;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
    private String clientPhotoURL;
    private List<String> educatedFor;
}
