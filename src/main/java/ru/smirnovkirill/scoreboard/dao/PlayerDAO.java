package ru.smirnovkirill.scoreboard.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smirnovkirill.scoreboard.model.Player;

@Component
public class PlayerDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PlayerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Player findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Player WHERE name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResult();
    }

    @Transactional
    public Player save(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
        return player;
    }
}
