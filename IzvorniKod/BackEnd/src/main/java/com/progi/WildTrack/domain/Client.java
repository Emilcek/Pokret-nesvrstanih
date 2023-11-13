package com.progi.WildTrack.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @Column(name = "ClientName" ,length = 30, nullable = false)
    private String clientName;
    @Column(name = "ClientPassword", length = 50, nullable = false)
    private String clientPassword;
    @Column(name = "ClientPhotoURL", nullable = false)
    private String clientPhotoURL;
    @Column(name = "FirstName", length = 50, nullable = false)
    private String firstName;
    @Column(name = "LastName", length = 50, nullable = false)
    private String lastName;
    @Column(name = "Email", length = 100, nullable = false)
    private String email;
    @Column(name = "isVerified", nullable = false)
    private boolean isVerified;
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Researcher researcher;
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Explorer explorer;
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private StationLead stationLead;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActionComment> actionComments;

    public Client() {
    }


}
