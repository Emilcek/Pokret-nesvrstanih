package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExplorerRepository extends JpaRepository<Explorer, String> {

    Explorer findByExplorerName(String clientName);

    boolean existsByExplorerName(String clientName);
    List<Explorer> findAllByStationIsNull();

    List<Explorer> findAllByStationStationName(String stationName);

}
