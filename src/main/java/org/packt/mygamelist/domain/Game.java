package org.packt.mygamelist.domain;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;

@Getter
@Setter
@Entity
public class Game {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private String name;

    private String summary;
    private int price;

    @Column(nullable = false)
    private boolean IsGameAvail;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private AppUser user;
    @Transient
    private ArrayList<String> platforms = new ArrayList<>(6);

    public Game(){}
    public Game(String name, String summary, int price, boolean IsGameAvail, ArrayList<String> platforms, AppUser user){
        super();
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.IsGameAvail = IsGameAvail;
        this.platforms = platforms;
        this.user = user;
    }
}
