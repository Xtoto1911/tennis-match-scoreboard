package ru.smirnovkirill.scoreboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smirnovkirill.scoreboard.model.Matches;
import ru.smirnovkirill.scoreboard.service.FinishedMatchesPersistenceService;
import ru.smirnovkirill.scoreboard.service.MatchScoreCalculationService;
import ru.smirnovkirill.scoreboard.service.OngoingMatchesService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/match")
public class MathController {
    private final OngoingMatchesService ongoingMatchesService;
    private final MatchScoreCalculationService scoreService;
    private final FinishedMatchesPersistenceService finishedService;

    @Autowired
    public MathController(OngoingMatchesService ongoingMatchesService, MatchScoreCalculationService scoreService, FinishedMatchesPersistenceService finishedService) {
        this.ongoingMatchesService = ongoingMatchesService;
        this.scoreService = scoreService;
        this.finishedService = finishedService;
    }

    @GetMapping
    public ResponseEntity<?> getMatch(@RequestParam("uuid") UUID uuid) {
        Matches match = ongoingMatchesService.get(uuid);
        if(match == null) {
            return ResponseEntity.badRequest().body("Match not found");
        }
        return ResponseEntity.ok(match);
    }

    @PostMapping("/add-point")
    public ResponseEntity<?> addPoint(@RequestParam("uuid") UUID uuid, @RequestParam("winnerId") int winnerId) {
        Matches match = ongoingMatchesService.get(uuid);
        if(match == null) {
            return ResponseEntity.badRequest().body("Match not found");
        }

        scoreService.addPoint(match, winnerId);

        if(match.isFinished()) {
            finishedService.finishMatch(uuid, match);
            return ResponseEntity.ok(Map.of(
                    "finished", "true",
                    "winner", match.getWinner().getName()
            ));
        }

        return ResponseEntity.ok(match);
    }
}
