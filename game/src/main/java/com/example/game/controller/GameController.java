package com.example.game.controller;

import com.example.game.repository.GameRepository;
import com.example.game.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    GameService gameService;

    @GetMapping("/play/{player}")
    public Mono<String> getResult(@PathVariable String player)
    {
        log.info("User Entered GameController.getResult");
        return gameService.getResult(player);
    }
}
