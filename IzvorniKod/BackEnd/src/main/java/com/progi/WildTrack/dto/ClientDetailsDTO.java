package com.progi.WildTrack.dto;

import com.progi.WildTrack.domain.Client;
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
    private byte[] clientPhoto;

    public ClientDetailsDTO(Client client) {
        this.clientName = client.getClientName();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.role = client.getRole();
        this.clientPhoto = client.getClientPhoto();
    }
}


