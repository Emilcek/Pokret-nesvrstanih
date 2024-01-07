package com.progi.WildTrack.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

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
    private List<Request> Requests;


    public StationLead(Client savedClient, Status status) {
        this.client = savedClient;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationLead that = (StationLead) o;
        return Objects.equals(stationLeadName, that.stationLeadName);
        // Check equality based on unique identifier or fields
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationLeadName);
        // Generate hash code based on the unique identifier
    }
}
