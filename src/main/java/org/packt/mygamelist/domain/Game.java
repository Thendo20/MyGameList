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

    @Column(nullable = false)
    private boolean IsGameAvail;

    @Column
    private String platforms;

    @ManyToMany(mappedBy = "games")
    private List<AppUser> users;


    public Game(String name, String summary, int price, boolean IsGameAvail, String platforms, List<AppUser> users) {
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.IsGameAvail = IsGameAvail;
        this.platforms = platforms;
        this.users = users;
    }
}
