package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Types;
import java.util.Collection;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client implements UserDetails {
    @Id
    @Column(name = "ClientName" ,length = 30, nullable = false)
    private String clientName;
    @Column(name = "ClientPassword", nullable = false)
    private String clientPassword;
    @JdbcTypeCode(Types.VARBINARY)
    @Column(name = "ClientPhoto", nullable = false)
    private byte[] clientPhoto;
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

    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return clientPassword;
    }

    @Override
    public String getUsername() {
        return clientName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        // include only the fields that uniquely identify a Client
        result = prime * result + ((clientName == null) ? 0 : clientName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }
}
