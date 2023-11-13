package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.AnimalLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalLocationRepository extends JpaRepository<AnimalLocation,String> {
}
