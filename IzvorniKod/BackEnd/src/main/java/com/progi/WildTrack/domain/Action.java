package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActionId", nullable = false)
    private Long actionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Researchername")
    private Researcher researcher;
    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActionComment> actionComments;
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Explorer_Action",
            joinColumns = { @JoinColumn(name = "ActionId") },
            inverseJoinColumns = { @JoinColumn(name = "Explorername") }
    )
    Set<Explorer> explorers = new HashSet<>();
    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actionId == null) ? 0 : actionId.hashCode());
        return result;
    }

}

