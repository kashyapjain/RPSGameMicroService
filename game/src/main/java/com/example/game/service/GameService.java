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
        return gameRepository.getMovesLength().flatMap(len ->
        {
            int moveIndex = new Random().nextInt(len);
            return  moves.elementAt(moveIndex);
        });
    }

    public Mono<String> getResult(String player)
    {
        return inputIsValid(player).flatMap(valid ->
        {
            if(valid)
            {
                return getRandomMove().flatMap(computer -> getWinner(computer,player));
            }
            return Mono.just("Invalid input");
        });
    }

    private Mono<Boolean> inputIsValid(String player) {
        return moves.any(item -> item.equals(player));
    }

    private Mono<String> getWinner(String computer,String player)
    {
        if(computer.equals(player))
        {
            return Mono.just("It is a tie");
        }

        return moves.elementAt(0).flatMap(rock ->
                moves.elementAt(1).flatMap(paper ->
                {
                    return moves.elementAt(2).flatMap(scissors ->
                    {
                        if(player.equals(rock) && computer.equals(scissors))
                        {
                            return Mono.just("Player wins");
                        }

                        if(player.equals(paper) && computer.equals(rock))
                        {
                            return Mono.just("Player wins");
                        }

                        if(player.equals(scissors) && computer.equals(paper))
                        {
                            return Mono.just("Player wins");
                        }

                        return Mono.just("Computer wins");
                    }) ;
                }));
    }
}
