package org.packt.mygamelist.web;

import org.packt.mygamelist.domain.Game;
import org.packt.mygamelist.services.GameService;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames(@RequestParam(value = "gameAvail", required = false) Boolean gameAvail) {
        if(gameAvail == null) {
            List<Game> games = gameService.findAll();
            return ResponseEntity.ok().body(games);
        }
        else {
            List<Game> games = gameService.findByGameAvail(gameAvail);
            return ResponseEntity.ok().body(games);
        }
    }

    @GetMapping("/game/{name}")
    public ResponseEntity<Optional<Game>> getGameByName(@PathVariable("name") String name) {
        Optional<Game> game = gameService.findByName(name);
        return ResponseEntity.ok().body(game);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Game>> getGamesByPrice(@PathVariable("price") int price) {
        List<Game> games = gameService.findByPrice(price);
        return ResponseEntity.ok().body(games);
    }

//    @GetMapping("/{gameAvail}")
//    public ResponseEntity<List<Game>> getGamesByGameAvail(@RequestParam("gameAvail") boolean IsGameAvail) {
//        List<Game> games = gameService.findByGameAvail(IsGameAvail);
//        return ResponseEntity.ok().body(games);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGameById(@PathVariable("id") Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/byName/{name}")
    public ResponseEntity<Game> deleteGameByName(@PathVariable("name") String name) {
        gameService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{name}")
    public ResponseEntity<Game> updateGame(@RequestBody Game updatedGame) {
        Optional<Game> updated = gameService.update(updatedGame);
        return updated
                .map(value-> ResponseEntity.ok().body(value)).orElseGet(()-> {
                    Game created = gameService.create(updatedGame);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("{name}")
                            .buildAndExpand(created.getName())
                            .toUri();
                    return ResponseEntity.created(location).body(created);
                });

    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game created = gameService.create(game);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{name}")
                .buildAndExpand(created.getName())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
