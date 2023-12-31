package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Action;
import com.progi.WildTrack.domain.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findAllByResearcher(Researcher researcher);
}
