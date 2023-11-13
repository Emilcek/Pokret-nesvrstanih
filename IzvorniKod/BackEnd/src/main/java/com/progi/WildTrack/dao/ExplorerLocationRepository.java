package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.ExplorerLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExplorerLocationRepository extends JpaRepository<ExplorerLocation, String> {
}
