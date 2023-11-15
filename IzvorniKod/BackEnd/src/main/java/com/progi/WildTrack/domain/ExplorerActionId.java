package com.progi.WildTrack.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ExplorerActionId implements java.io.Serializable{
    private Long explorerActionId;
    private String explorerName;

    public ExplorerActionId() {
    }

    public ExplorerActionId(Long explorerActionId, String explorerName) {
        this.explorerActionId = explorerActionId;
        this.explorerName = explorerName;
    }

    public Long getExplorerActionId() {
        return explorerActionId;
    }

    public void setExplorerActionId(Long explorerActionId) {
        this.explorerActionId = explorerActionId;
    }

    public String getExplorerName() {
        return explorerName;
    }

    public void setExplorerName(String explorerName) {
        this.explorerName = explorerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExplorerActionId that)) return false;
        return Objects.equals(getExplorerActionId(), that.getExplorerActionId()) && Objects.equals(getExplorerName(), that.getExplorerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExplorerActionId(), getExplorerName());
    }
}
