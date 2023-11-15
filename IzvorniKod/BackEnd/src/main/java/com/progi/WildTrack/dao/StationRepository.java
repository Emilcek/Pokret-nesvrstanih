package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.domain.StationLead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByStationLead(StationLead stationLead);
}
