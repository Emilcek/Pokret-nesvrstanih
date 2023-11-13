package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearcherRepository extends JpaRepository<Researcher, String> {
}
