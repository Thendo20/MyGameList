package org.packt.mygamelist.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.packt.mygamelist.domain.Game;
import org.packt.mygamelist.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name = "Game Controller", description = "Controller for managing games")
@RestController
@RequestMapping("api/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    @Operation(summary = "Get all available or unavailable games")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of games returned successfully")
    })
    public ResponseEntity<List<Game>> getGames(@RequestParam(value = "gameAvail", required = false) Boolean gameAvail) {
        if (gameAvail == null) {
            List<Game> games = gameService.findAll();
            return ResponseEntity.ok().body(games);
        } else {
            List<Game> games = gameService.findByGameAvail(gameAvail);
            return ResponseEntity.ok().body(games);
        }
    }

    @GetMapping("/game/{name}")
    @Operation(summary = "Get games by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game returned successfully")
    })
    public ResponseEntity<Optional<Game>> getGameByName(@PathVariable("name") String name) {
        Optional<Game> game = gameService.findByName(name);
        return ResponseEntity.ok().body(game);
    }

    @GetMapping("/byPrice/{price}")
    @Operation(summary = "Get games by price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of game/s returned successfully")
    })
    public ResponseEntity<List<Game>> getGamesByPrice(@PathVariable("price") int price) {
        List<Game> games = gameService.findByPrice(price);
        return ResponseEntity.ok().body(games);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete game by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully")
    })
    public ResponseEntity<Game> deleteGameById(@PathVariable("id") Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/byName/{name}")
    @Operation(summary = "Delete game by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully")
    })
    public ResponseEntity<Game> deleteGameByName(@PathVariable("name") String name) {
        gameService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{name}")
    @Operation(summary = "Update existing game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game updated successfully")
    })
    public ResponseEntity<Game> updateGame(@RequestBody Game updatedGame) {
        Optional<Game> updated = gameService.update(updatedGame);
        return updated
                .map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> {
                    Game created = gameService.create(updatedGame);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("{name}")
                            .buildAndExpand(created.getName())
                            .toUri();
                    return ResponseEntity.created(location).body(created);
                });

    }

    @PostMapping
    @Operation(summary = "Create game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created successfully")
    })
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game created = gameService.create(game);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{name}")
                .buildAndExpand(created.getName())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
