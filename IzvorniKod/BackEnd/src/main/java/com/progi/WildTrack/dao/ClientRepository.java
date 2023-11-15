package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientName(String clientName);

    Boolean existsByClientName(String clientName);

    Boolean existsByEmail(String email);

}
