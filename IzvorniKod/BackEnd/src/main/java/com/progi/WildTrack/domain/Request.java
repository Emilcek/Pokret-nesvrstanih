package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestId", nullable = false)
    private Long requestId;

    @Column(name = "RequestStatus", length = 30, nullable = false)
    private String requestStatus;

//    @Column(name = "NumOfPeople", nullable = false)
//    private int numOfPeople;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="StationLeadname")
    private StationLead stationLead;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Researchername")
    private Researcher researcher;

}
