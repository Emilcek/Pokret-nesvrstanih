package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.StationLead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationLeadRepository extends JpaRepository<StationLead, Long> {
    List<StationLead> findAllByStatusStatusId(Long statusId);

    StationLead findByStationLeadName(String clientName);
}
