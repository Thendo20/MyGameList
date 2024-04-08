//package org.packt.mgldatabase.services;
//
//import org.packt.mgldatabase.domain.AppUser;
//import org.packt.mgldatabase.domain.Game;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class GameService {
//    private final CrudRepository<Game,Long> repository;
//    public GameService(CrudRepository<Game,Long> repository){
//        this.repository = repository;
//        this.repository.saveAll(defaultGames());
//    }
//
//    defaultGames(){
//        ArrayList<String> platforms;
//        return List.of(
//                new Game("GTA 5", "When a young street hustler,a retired bank robber, and a terrifying psychopath find themselves"+
//                         "entangled with some of the most frightening and deranged elements of the criminal underworld,"+
//                         "the U.S. Government, and the entertainment industry, they must pull off a series of dangerous"+
//                          "heists to survive in a ruthless city in which they can trust nobody--least of all one another.", 500, true, platforms)
//        )
//
//    }
//
//
//}
