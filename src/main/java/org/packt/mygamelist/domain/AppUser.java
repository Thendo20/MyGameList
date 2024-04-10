package org.packt.mygamelist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Getter
@Setter
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private int no_liked_games = 0;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")

    private List<Game> games;


    public AppUser(){
    }
    public AppUser(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }

    public void inc_liked_games(){
        this.no_liked_games++;
    }

    public int no_like_games(){
        return this.no_liked_games;
    }

}
