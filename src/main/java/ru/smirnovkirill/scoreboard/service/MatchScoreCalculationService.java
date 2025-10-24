package ru.smirnovkirill.scoreboard.service;

import org.springframework.stereotype.Service;
import ru.smirnovkirill.scoreboard.model.Matches;

@Service
public class MatchScoreCalculationService {

    // заглушка логики расчета очков
    public void addPoint(Matches match, int playerId) {
        System.out.println("WinnerId = " + playerId);
        System.out.println("Player1Id = " + match.getPlayer1().getId());
        System.out.println("Player2Id = " + match.getPlayer2().getId());
        if(match.getPlayer1().getId() == playerId) {
            match.setSetsPlayer1(match.getSetsPlayer1() + 1);
        } else {
            match.setSetsPlayer2(match.getSetsPlayer2() + 1);
        }

        if(match.getSetsPlayer1() == 2 || match.getSetsPlayer2() == 2) {
            match.setFinished(true);
            match.setWinner(match.getSetsPlayer1() > match.getSetsPlayer2()
                    ? match.getPlayer1() : match.getPlayer2()
            );
        }
    }
}
