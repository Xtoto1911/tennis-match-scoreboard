package ru.smirnovkirill.scoreboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smirnovkirill.scoreboard.dao.PlayerDAO;
import ru.smirnovkirill.scoreboard.model.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerCacheService {

    private final PlayerDAO playerDAO;
    private final Map<String, Player> players = new ConcurrentHashMap<>();

    @Autowired
    public PlayerCacheService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public Player getOrCreate(String name) {
        if(players.containsKey(name)) {
            return players.get(name);
        }

        Player player = playerDAO.findByName(name);
        if(player == null) {
            player = new Player();
            player.setName(name);
            playerDAO.save(player);
        }

        players.put(name, player);
        return player;
    }
}
