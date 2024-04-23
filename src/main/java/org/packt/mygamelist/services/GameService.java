package org.packt.mygamelist.services;

import org.packt.mygamelist.domain.Game;
import org.packt.mygamelist.domain.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;
    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.gameRepository.saveAll(defaultGames());
    }

    private static List<Game> defaultGames() {
        return List.of(
                new Game("GTA 5", "When a young street hustler,a retired bank robber, and a terrifying psychopath find themselves" +
                        "entangled with some of the most frightening and deranged elements of the criminal underworld," +
                        "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous" +
                        "heists to survive in a ruthless city in which they can trust nobody--least of all one another.", 500, true,
                        "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows"),
                new Game("Horizon Forbidden West", "The player controls Aloy, a hunter who ventures into an uncharted frontier known as the Forbidden West, " +
                        "a post-apocalyptic version of the Western United States to investigate a mysterious plague. " +
                        "Aloy's primary weapon of use is bows and arrows with different ranges and rate of fire.", 800, true, "Playstation 4/5, Windows"),
                new Game("God of War Ragnorak", "God of War Ragnarök is an action-adventure game developed by Santa Monica Studio " +
                        "and published by Sony Interactive Entertainment.It was released worldwide on November 9, 2022, for both the PlayStation 4 and PlayStation 5," +
                        " marking the first cross-gen release in the God of War series", 900, true, "Playstation 4/5")
        );

    }

    public List<Game> findAll() {
        List<Game> list = new ArrayList<>();
        Iterable<Game> games = gameRepository.findAll();
        games.forEach(list::add);
        return list;
    }

    public Optional<Game> findByName(String name) {
        return gameRepository.findByName(name);
    }

    public List<Game> findByPrice(int price) {
        return gameRepository.findByPrice(price);
    }

    public List<Game> findByIsGameAvail(boolean isGameAvail) {
        return gameRepository.findByIsGameAvail(isGameAvail);
    }

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public void delete(String name) {
        gameRepository.deleteByName(name);
    }

    public Optional<Game> update(Game newGame) {

        return gameRepository.findByName(newGame.getName()).map( game -> {
            game.setName(newGame.getName());
            game.setSummary(newGame.getSummary());
            game.setPrice(newGame.getPrice());
            game.setIsGameAvail(newGame.getIsGameAvail());
            game.setPlatforms(newGame.getPlatforms());

            return gameRepository.save(game);
        });

    }

}