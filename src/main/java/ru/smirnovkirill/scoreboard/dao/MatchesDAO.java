package ru.smirnovkirill.scoreboard.dao;

import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smirnovkirill.scoreboard.model.Matches;

import java.util.List;

@Component
public class MatchesDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public MatchesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Matches> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Matches", Matches.class)
                .getResultList();
    }

    @Transactional
    public void save(Matches match) {
        sessionFactory.getCurrentSession().persist(match);
    }
}
