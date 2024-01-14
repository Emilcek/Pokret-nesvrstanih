package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Explorer;
import com.progi.WildTrack.domain.ExplorerLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ExplorerLocationRepository extends JpaRepository<ExplorerLocation, String> {

    ExplorerLocation findFirstByExplorer_ExplorerNameOrderByLocationTimestampDesc(String explorerName);

    Set<ExplorerLocation> findAllByExplorer_ExplorerName(String explorerName);
}
