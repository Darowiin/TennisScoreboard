package com.dao;

import com.models.Match;
import com.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class MatchDaoImpl implements GenericDao<Match> {
    @Override
    public void save(Match match) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(match);
            transaction.commit();
        }
        catch (Exception e) {
            System.out.println("Error saving match: " + e.getMessage());
        }
    }

    @Override
    public Optional<Match> getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            if (id <= 0) {
                return Optional.empty();
            }
            Match match = session.get(Match.class, id);
            if (match == null) {
                return Optional.empty();
            }
            return Optional.of(match);
        } catch (Exception e) {
            System.out.println("Error retrieving match by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Match>> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            List<Match> matches = session.createQuery("FROM Match", Match.class).list();
            return Optional.of(matches);
        } catch (Exception e) {
            System.out.println("Error retrieving all matches: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void update(Match match) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Match existingMatch = session.get(Match.class, match.getID());
            if (existingMatch != null) {
                existingMatch.setWinner(match.getWinner());

                session.update(existingMatch);
                transaction.commit();
            } else {
                System.out.println("Match with ID " + match.getID() + " does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error updating match: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Match match = session.get(Match.class, id);
            if (match != null) {
                session.delete(match);
                transaction.commit();
            } else {
                System.out.println("Match with ID " + id + " does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting match: " + e.getMessage());
        }
    }
}
