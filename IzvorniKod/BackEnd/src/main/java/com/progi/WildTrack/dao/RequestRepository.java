package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Request;
import com.progi.WildTrack.domain.Researcher;
import com.progi.WildTrack.domain.StationLead;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByStationLead(StationLead stationLead);

    Request findByRequestId(Long requestId);

    List<Request> findAllByResearcher(Researcher researcher);
}
