package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
