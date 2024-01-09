package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Explorer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExplorerRepository extends JpaRepository<Explorer, String> {

    Explorer findByExplorerName(String clientName);
    boolean existsByExplorerName(String clientName);
}
