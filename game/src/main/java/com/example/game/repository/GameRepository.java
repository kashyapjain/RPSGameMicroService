package com.example.game.repository;

import com.example.game.GameApplication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Repository
public class GameRepository {
    public GameRepository(){}
    private String[] moves = {"Rock", "Paper","Scissors"};
    public Flux<String> getMoves()
    {
        return Flux.fromArray(moves);
    }

    public Mono<Integer> getMovesLength()
    {
        return Mono.just(moves.length);
    }
}
