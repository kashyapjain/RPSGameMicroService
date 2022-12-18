package com.example.game.service;

import com.example.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
public class GameService {
    GameRepository gameRepository;

    private Flux<String> moves;

    @Autowired
    public GameService(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
        this.moves = gameRepository.getMoves();
    }

    private Mono<String> getRandomMove()
    {
        int len = gameRepository.getMovesLength().block();
        int moveIndex = new Random().nextInt(len);
        return  moves.elementAt(moveIndex);
    }

    public Mono<String> getResult(String player)
    {
        return getRandomMove().flatMap(move -> Mono.just(getWinner(move,player)));
    }

    private String getWinner(String computer,String player)
    {
        String Rock = "Rock";
        String Paper = "Paper";
        String Scissors = "Scissors";

        if(computer == player)
        {
            return "It is a tie";
        }

        if(player.equals(Rock) && computer.equals(Scissors))
        {
            return "Player wins";
        }

        if(player.equals(Paper) && computer.equals(Rock))
        {
            return "Player wins";
        }

        if(player.equals(Scissors) && computer.equals(Paper))
        {
            return "Player wins";
        }

        return "Computer wins";
    }
}
