package com.progi.WildTrack.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class EducatedForId implements Serializable {
    private Long vehicleId;
    private String explorerName;

    public EducatedForId() {
    }

    public EducatedForId(Long vehicleId, String explorerName) {
        this.vehicleId = vehicleId;
        this.explorerName = explorerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EducatedForId that)) return false;
        return Objects.equals(vehicleId, that.vehicleId) && Objects.equals(explorerName, that.explorerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, explorerName);
    }
}
