package org.packt.mygamelist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private String name;
    @Column
    private String summary;
    @Column
    private int price;

    @Column(nullable = false, name = "is_game_avail")
    private boolean gameAvail;

    @Column
    private String platforms;

    @ManyToMany(mappedBy = "games")
    private List<AppUser> users;


    public Game(String name, String summary, int price, boolean gameAvail, String platforms) {
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.gameAvail = gameAvail;
        this.platforms = platforms;
    }
}
