package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.ExplorerAction;
import com.progi.WildTrack.domain.ExplorerActionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExplorerActionRepositroy extends JpaRepository<ExplorerAction, ExplorerActionId> {
}
