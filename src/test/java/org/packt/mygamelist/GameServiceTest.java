package org.packt.mygamelist;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.packt.mygamelist.domain.Game;
import org.packt.mygamelist.domain.GameRepository;
import org.packt.mygamelist.services.GameService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private GameService gameService;
    private AutoCloseable autoCloseable;

    private final String summary = "When a young street hustler,a retired bank robber, and a terrifying psychopath find themselves" +
            "entangled with some of the most frightening and deranged elements of the criminal underworld," +
            "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous" +
            "heists to survive in a ruthless city in which they can trust nobody--least of all one another.";

    @BeforeEach
    void init() {
        autoCloseable = MockitoAnnotations.openMocks(GameServiceTest.class);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findAllGames() {
        when(gameRepository.findAll()).thenReturn(List.of(new Game("GTA 5", summary, 500, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows")));

        List<Game> games = gameService.findAll();
        assertThat(games.size()).isEqualTo(1);
        assertThat(games.get(0).getName()).isEqualTo("GTA 5");
    }

    @Test
    void createGame() {
        String summary = "Hollow Knight is a 2D side-scrolling Metroidvania. " +
                "The player controls an insectoid, silent protagonist called \"the Knight\" " +
                "who explores an underground fallen kingdom called Hallownest. The Knight can strike " +
                "enemies with a sword-like weapon called a Nail and can learn spells that allow for long-range attacks.";

        when(gameRepository.save(any())).thenReturn(new Game("Hollow Knight", summary, 500, true,
                "Playstation 4/5, Xbox one/series X/series S/ Windows"));
        when(gameRepository.findByName(any())).thenReturn(Optional.of(new Game("Hollow Knight", summary, 500, true,
                "Playstation 4/5, Xbox one/series X/series S/ Windows")));

        Game newGame = new Game("Hollow Knight", summary,
                500, true, "Playstation 4/5, Xbox one/series X/series S/ Windows");
        gameService.create(newGame);
        Optional<Game> games = gameService.findByName("Hollow Knight");
        assertThat(games.isPresent()).isEqualTo(true);
        assertThat(games.get().getName()).isEqualTo("Hollow Knight");
    }

    @Test
    void updateGame() {
        Game game = new Game("GTA 5", summary, 500, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows");
        Game updated = new Game("GTA 5", summary, 1000, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows");

        when(gameRepository.findByName(any())).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(updated);

        Game newGame = new Game("GTA 5", summary, 1000, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows");
        Optional<Game> updatedGame = gameService.update(newGame);
        assertThat(updatedGame.isPresent()).isEqualTo(true);
        assertThat(updatedGame.get().getPrice()).isEqualTo(1000);

    }

    @Test
    void findGameByPrice() {
        when(gameRepository.findByPrice(anyInt())).thenReturn(List.of(new Game("GTA 5", summary, 500, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows")));
        List<Game> games = gameService.findByPrice(500);
        assertThat(games.size() > 0).isTrue();
        assertThat(games.getFirst().getName()).isEqualTo("GTA 5");
    }

    @Test
    void findGameByIsGameAvail() {

        when(gameRepository.findByGameAvail(anyBoolean())).thenReturn(List.of(new Game("GTA 5", summary, 500, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows")));

        List<Game> games = gameService.findByGameAvail(true);
        assertThat(games.getFirst().isGameAvail()).isEqualTo(true);
    }

    @Test
    void deleteGame() {
        gameService.delete(1L);
        verify(gameRepository, times(1)).deleteById(anyLong());
    }
}
