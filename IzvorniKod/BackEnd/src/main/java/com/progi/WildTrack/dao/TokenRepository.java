package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join Client u\s
      on t.client.clientName = u.clientName\s
      where u.clientName = :name and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByClient(String name);

    Optional<Token> findByToken(String token);
}
