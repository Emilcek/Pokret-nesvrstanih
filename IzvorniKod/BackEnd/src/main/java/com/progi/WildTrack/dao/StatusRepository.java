package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Description;
import com.progi.WildTrack.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Object> findByDescription(Description description);

    Status findByStatusId(Integer status);
}
