package org.packt.mygamelist.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findByName(String name);

    List<Game> findByIsGameAvail(boolean isGameAvail);

    List<Game> findByPrice(int price);

    void deleteByName(String name);
}
