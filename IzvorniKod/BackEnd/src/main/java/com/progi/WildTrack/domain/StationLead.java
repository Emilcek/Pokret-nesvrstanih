package com.progi.WildTrack.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "StationLead")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "stationLeadName")
public class StationLead {
    @Id
    @Column(name = "StationLeadname" ,length = 30, nullable = false)
    private String stationLeadName;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "StatusId")
    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Clientname", referencedColumnName = "StationLeadname")
    @MapsId
    private Client client;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "StationId")
    private Station station;
    @OneToMany(mappedBy = "stationLead", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Action> actions;


    public StationLead(Client savedClient, Status status) {
        this.client = savedClient;
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stationLeadName == null) ? 0 : stationLeadName.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }
}
