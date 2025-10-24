package ru.smirnovkirill.scoreboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smirnovkirill.scoreboard.dao.MatchesDAO;
import ru.smirnovkirill.scoreboard.model.Matches;

import java.util.UUID;

@Service
public class FinishedMatchesPersistenceService {

    private final MatchesDAO matchesDAO;
    private final OngoingMatchesService ongoingMatchesService;

    @Autowired
    public FinishedMatchesPersistenceService(MatchesDAO matchesDAO, OngoingMatchesService ongoingMatchesService) {
        this.matchesDAO = matchesDAO;
        this.ongoingMatchesService = ongoingMatchesService;
    }

    public void finishMatch(UUID uuid, Matches match) {
        matchesDAO.save(match);
        ongoingMatchesService.remove(uuid);
    }
}
