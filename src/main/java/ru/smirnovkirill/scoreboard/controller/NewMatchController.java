package ru.smirnovkirill.scoreboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.smirnovkirill.scoreboard.model.Matches;
import ru.smirnovkirill.scoreboard.model.Player;
import ru.smirnovkirill.scoreboard.service.OngoingMatchesService;
import ru.smirnovkirill.scoreboard.service.PlayerCacheService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/new-match")
public class NewMatchController {

    private final PlayerCacheService playerCacheService;
    private final OngoingMatchesService ongoingMatchesService;

    @Autowired
    public NewMatchController(PlayerCacheService playerCacheService, OngoingMatchesService ongoingMatchesService) {
        this.playerCacheService = playerCacheService;
        this.ongoingMatchesService = ongoingMatchesService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createNewMath(@RequestParam("player1Name") String player1Name,
                                                             @RequestParam("player2Name") String player2Name) {
        try {
            if (player1Name.equals(player2Name)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Игрок не может играть сам с собой"));
            }

            Player p1 = playerCacheService.getOrCreate(player1Name);
            Player p2 = playerCacheService.getOrCreate(player2Name);

            Matches match = new Matches();
            match.setPlayer1(p1);
            match.setPlayer2(p2);

            UUID uuid = ongoingMatchesService.createMatch(match);
            return ResponseEntity.ok(Map.of("uuid", uuid.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

}
