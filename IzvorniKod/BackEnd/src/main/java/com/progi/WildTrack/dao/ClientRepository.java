package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
