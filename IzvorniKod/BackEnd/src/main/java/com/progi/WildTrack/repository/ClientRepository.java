package com.progi.WildTrack.repository;

import com.progi.WildTrack.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
@Query(value = """
      select c from Client c
      where c.client_name = :username
      """)
    Optional<Client> findByUsername(String username);

    boolean existsByEmail(String clientName);

//    boolean existsByClient_name(String clientName);
}
