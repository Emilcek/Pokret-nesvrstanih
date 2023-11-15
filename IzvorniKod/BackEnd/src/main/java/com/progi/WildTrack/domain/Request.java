package com.progi.WildTrack.domain;

import jakarta.persistence.*;
@Entity
@Table(name = "Request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestId", nullable = false)
    private Long requestId;

    @Column(name = "RequestStatus", length = 30, nullable = false)
    private String requestStatus;

    @Column(name = "NumOfPeople", nullable = false)
    private int numOfPeople;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="StationLeadname")
    private StationLead stationLead;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Researchername")
    private Researcher researcher;

    public Request() {
    }

}
