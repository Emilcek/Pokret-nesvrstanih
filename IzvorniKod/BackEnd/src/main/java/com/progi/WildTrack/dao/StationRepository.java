package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
