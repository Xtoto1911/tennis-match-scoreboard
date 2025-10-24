package ru.smirnovkirill.scoreboard.service;

import org.springframework.stereotype.Service;
import ru.smirnovkirill.scoreboard.model.Matches;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatchesService {

    private final Map<UUID, Matches> matches = new ConcurrentHashMap<>();

    public UUID createMatch(Matches match) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid,match);
        return uuid;
    }

    public Matches get(UUID uuid) {
        return matches.get(uuid);
    }

    public void remove(UUID uuid) {
        matches.remove(uuid);
    }

}
