package com.personal.graphql.service;

import com.personal.graphql.model.Player;
import com.personal.graphql.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {
    private List<Player> players = new ArrayList<>();
    AtomicInteger id = new AtomicInteger(0);

    public List<Player> findAll() {
        return players;
    }

    public Optional<Player> findOne(Integer id) {
        return players.stream()
                .filter(player -> Objects.equals(player.id(), id)).findFirst();
    }

    public Player create(String name, Team team) {
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);
        return player;
    }

    public Player delete(Integer id) {
        Player player1 = players.stream()
                .filter(player -> Objects.equals(player.id(), id)).findFirst().orElseThrow(() -> new RuntimeException(""));
        players.remove(player1);
        return player1;
    }

    public Player update(Integer id, String name, Team team) {
        Player updatedPlayer = new Player(id, name, team);
        Optional<Player> optionalPlayer = players.stream()
                .filter(player -> Objects.equals(player.id(), id)).findFirst();
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            int index = players.indexOf(player);
            players.set(index, updatedPlayer);
        } else {
            throw new IllegalArgumentException("Invalid Player");
        }
        return updatedPlayer;
    }

    @PostConstruct
    public void init() {
        players.add(new Player(id.incrementAndGet(), "MSD", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Rohit", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Jasprit", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Rishab", Team.DC));
        players.add(new Player(id.incrementAndGet(), "Suresh", Team.CSK));
    }
}
