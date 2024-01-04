package com.progi.WildTrack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AnimalLocation")
public class AnimalLocation {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AnimalLocationTS", nullable = false)
    private Timestamp animalLocationTS;
    @Id
    @Column(name="LocationofAnimal",length = 30, nullable = false)
    private String locationofAnimal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AnimalId")
    private Animal animal;

    @Override
    public String toString() {
        return "AnimalLocation{" +
                "animalLocationTS=" + animalLocationTS +
                ", locationofAnimal='" + locationofAnimal + '\'' +
                ", animal=" + animal +
                '}';
    }


}
