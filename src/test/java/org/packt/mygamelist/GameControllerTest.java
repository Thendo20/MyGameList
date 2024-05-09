package org.packt.mygamelist;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.packt.mygamelist.domain.Game;
import org.packt.mygamelist.domain.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    static MariaDBContainer<?> mariadb = new MariaDBContainer<>(
            "mariadb:10.5.8"
    );
    @Autowired
    GameRepository gameRepository;
    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        mariadb.start();
    }

    @AfterAll
    static void afterAll() {
        mariadb.stop();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        gameRepository.deleteAll();
    }

    @Test
    void shouldGetAllGames() {
        String summary1 = "Then a young street hustler,a retired bank robber, and a terrifying psychopath find themselves" +
                "entangled with some of the most frightening and deranged elements of the criminal underworld," +
                "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous" +
                "heists to survive in a ruthless city in which they can trust nobody--least of all one another.";
        String summary2 = "The player controls Aloy, a hunter who ventures into an uncharted frontier known as the Forbidden West, " +
                "a post-apocalyptic version of the Western United States to investigate a mysterious plague. " +
                "Aloy's primary weapon of use is bows and arrows with different ranges and rate of fire.";
        String summary3 = "God of War Ragnarök is an action-adventure game developed by Santa Monica Studio " +
                "and published by Sony Interactive Entertainment.It was released worldwide on November 9, 2022, for both the PlayStation 4 and PlayStation 5," +
                " marking the first cross-gen release in the God of War series";

        List<Game> games = List.of(new Game("GTA 5", summary1, 500, true,
                        "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows"),
                new Game("Horizon Forbidden West", summary2, 800, true, "Playstation 4/5, Windows"),
                new Game("God of War Ragnorak", summary3, 900, true, "Playstation 4/5")
        );
        gameRepository.saveAll(games);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/games")
                .then()
                .statusCode(200)
                .body("success", hasSize(3));
    }
    @Test
    void shouldGetGameByName() {
        String summary1 = "Then a young street hustler,a retired bank robber, and a terrifying psychopath find themselves" +
                "entangled with some of the most frightening and deranged elements of the criminal underworld," +
                "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous" +
                "heists to survive in a ruthless city in which they can trust nobody--least of all one another.";
        Game game = new Game("GTA 5", summary1, 500, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows");
        gameRepository.save(game);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/games/byName/GTA 5")
                .then()
                .statusCode(200)
                .body("success", hasSize(1));

    }
    @Test
    void shouldGetGameByPrice() {
        String summary1 = "Then a young street hustler,a retired bank robber, and a terrifying psychopath find themselves" +
                "entangled with some of the most frightening and deranged elements of the criminal underworld," +
                "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous" +
                "heists to survive in a ruthless city in which they can trust nobody--least of all one another.";
        Game game = new Game("GTA 5", summary1, 500, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows");
        gameRepository.save(game);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/games/500")
                .then()
                .statusCode(200)
                .body("success", hasSize(1));
    }
//    @Test
//    void shouldGetGameByIsGameAvailable() {
//        String summary1 = "Then a young street hustler,a retired bank robber, and a terrifying psychopath find themselves" +
//                "entangled with some of the most frightening and deranged elements of the criminal underworld," +
//                "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous" +
//                "heists to survive in a ruthless city in which they can trust nobody--least of all one another.";
//        Game game = new Game("GTA 5", summary1, 500, true,
//                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows");
//        gameRepository.save(game);
//
//        given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/api/games/true")
//                .then()
//                .statusCode(200)
//                .body("success", hasSize(1));
//    }
    @Test
    void shouldDeleteGameByName() {
        String summary1 = "Then a young street hustler,a retired bank robber, and a terrifying psychopath find themselves" +
                "entangled with some of the most frightening and deranged elements of the criminal underworld," +
                "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous" +
                "heists to survive in a ruthless city in which they can trust nobody--least of all one another.";
        Game game = new Game("GTA 5", summary1, 500, true,
                "Playstation 3/4/5, Xbox 360/one/series s/series x, Windows");
        gameRepository.save(game);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/games/delete/GTA 5")
                .then()
                .statusCode(200);
    }


}
