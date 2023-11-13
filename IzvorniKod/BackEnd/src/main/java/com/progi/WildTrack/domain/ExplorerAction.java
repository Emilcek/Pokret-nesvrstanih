package com.progi.WildTrack.domain;

import jakarta.persistence.*;
@Entity

@Table(name = "ExplorerAction")
public class ExplorerAction {
    @EmbeddedId
    private ExplorerActionId explorerActionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ExplorerActionId", referencedColumnName = "ActionId")
    @MapsId
    private Action action;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Explorername", columnDefinition = "varchar(30)")
    @MapsId
    private Explorer explorer;

    public ExplorerAction() {
    }

}
