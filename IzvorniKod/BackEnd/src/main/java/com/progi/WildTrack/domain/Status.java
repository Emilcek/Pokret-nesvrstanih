package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "Status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusId", nullable = false)
    private Long statusId;
    @Column(name = "Description", nullable = false)
    @Enumerated(EnumType.STRING)
    private Description description;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Researcher> researchers;
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StationLead> stationLeads;

    public Status() {}

}
