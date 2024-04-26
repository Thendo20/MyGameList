package org.packt.mygamelist.web;

import org.packt.mygamelist.domain.Game;
import org.packt.mygamelist.services.GameService;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.GetMapping;

@RepositoryRestResource
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public Iterable<Game> getGames() {
        return gameService.findAll();
    }

    @GetMapping("/games/{name}")
    public Game getGameByName(String name) {
        return gameService.findByName(name).orElse(null);
    }

    @GetMapping("/games/price/{price}")
    public Iterable<Game> getGamesByPrice(int price) {
        return gameService.findByPrice(price);
    }

    @GetMapping("/games/isGameAvail/{isGameAvail}")
    public Iterable<Game> getGamesByIsGameAvail(boolean isGameAvail) {
        return gameService.findByIsGameAvail(isGameAvail);
    }

    @GetMapping("/games/delete/{name}")
    public void deleteGameByName(String name) {
        gameService.delete(name);
    }

    @GetMapping("/games/update")
    public Game updateGame(Game newGame) {
        return gameService.update(newGame).orElse(null);
    }

    @GetMapping("/games/create")
    public Game createGame(Game game) {
        return gameService.create(game);
    }
}
