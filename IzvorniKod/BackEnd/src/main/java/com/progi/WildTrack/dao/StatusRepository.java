package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
