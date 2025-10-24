package ru.smirnovkirill.scoreboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smirnovkirill.scoreboard.dao.MatchesDAO;
import ru.smirnovkirill.scoreboard.model.Matches;


import java.util.List;


@RestController
@RequestMapping("/matches")
public class MatchesController {

    private final MatchesDAO matchesDAO;

    @Autowired
    public MatchesController(MatchesDAO matchesDAO) {
        this.matchesDAO = matchesDAO;
    }

    @GetMapping
    public ResponseEntity<List<Matches>> getAll() {
        List<Matches> matches = matchesDAO.getAll();

        return ResponseEntity.ok(matches);
    }
}
