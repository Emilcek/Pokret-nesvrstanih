package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
