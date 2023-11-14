package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResearcherRepository extends JpaRepository<Researcher, String> {
    List<Researcher> findAllByStatusStatusId(Long statusId);

    Researcher findByResearcherName(String clientName);
}
