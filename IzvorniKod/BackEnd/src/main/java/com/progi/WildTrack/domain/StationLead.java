package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "StationLead")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationLead {
    @Id
    @Column(name = "StationLeadname" ,length = 30, nullable = false)
    private String stationLeadName;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "StatusId")
    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Clientname", referencedColumnName = "StationLeadname")
    @MapsId
    private Client client;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "StationId",updatable = false, insertable = false)
    private Station station;
    @OneToMany(mappedBy = "stationLead", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> Requests;



}
