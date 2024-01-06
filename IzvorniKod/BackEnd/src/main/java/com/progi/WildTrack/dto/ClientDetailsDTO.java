package com.progi.WildTrack.dto;

import com.progi.WildTrack.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    private Set<String> educatedFor = new HashSet<>();
    private String stationName;
    private Description status;

    public ClientDetailsDTO(Client client) {
        setBaseAttributes(client);
    }

    public ClientDetailsDTO(Client client, Explorer explorer) {
       setBaseAttributes(client);
        if(explorer.getVehicles() != null){
            Set<Vehicle> educatedFor = explorer.getVehicles();
            for (Vehicle vehicle : educatedFor) {
                this.educatedFor.add(vehicle.getVehicleType());
            }
        }
    }

    public ClientDetailsDTO(Client client, StationLead stationLead) {
        setBaseAttributes(client);
        if (stationLead.getStatus() != null)
            this.status = stationLead.getStatus().getDescription();
        else
            this.status = null;

        if (stationLead.getStation() != null) {
            this.stationName = stationLead.getStation().getStationName();
        } else {
            this.stationName = null;
        }
    }

    public ClientDetailsDTO(Client client, Researcher researcher) {
        setBaseAttributes(client);
        if (researcher.getStatus() != null)
            this.status = researcher.getStatus().getDescription();
        else
            this.status = null;
    }


    private void setBaseAttributes(Client client) {
        this.clientName = client.getClientName();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.role = client.getRole();
        this.clientPhoto = client.getClientPhoto();
    }

}


