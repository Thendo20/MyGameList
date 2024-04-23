package org.packt.mygamelist.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findByName(@Param("name") String name);
    List<Game> findByIsGameAvail(@Param("isGameAvail") boolean isGameAvail);
    List<Game> findByPrice(@Param("price") int price);
    void deleteByName(@Param("name") String name);
}
