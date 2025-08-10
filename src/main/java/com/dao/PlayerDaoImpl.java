package com.dao;

import com.models.Player;
import com.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PlayerDaoImpl implements GenericDao<Player> {
    @Override
    public void save(Player player) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(player);
            transaction.commit();
        }
        catch (Exception e) {
            System.out.println("Error saving player: " + e.getMessage());
        }
    }

    @Override
    public Optional<Player> getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            if (id <= 0) {
                return Optional.empty();
            }
            Player player = session.get(Player.class, id);
            if (player == null) {
                return Optional.empty();
            }
            return Optional.of(player);
        } catch (Exception e) {
            System.out.println("Error retrieving player by ID: " + e.getMessage());
            return Optional.empty();
        }
    }
    public Optional<Player> getByName(String name) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            if (name == null || name.isEmpty()) {
                return Optional.empty();
            }
            Player player = session.createQuery("FROM Player WHERE Name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return Optional.ofNullable(player);
        } catch (Exception e) {
            System.out.println("Error retrieving player by name: " + e.getMessage());
            return Optional.empty();
        }
    }
    @Override
    public Optional<List<Player>> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            List<Player> players = session.createQuery("FROM Player", Player.class).list();
            return Optional.of(players);
        } catch (Exception e) {
            System.out.println("Error retrieving all players: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void update(Player player) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Player existingPlayer = session.get(Player.class, player.getID());
            if (existingPlayer != null) {
                existingPlayer.setName(player.getName());
                session.update(existingPlayer);
                transaction.commit();
            } else {
                System.out.println("Player with ID " + player.getID() + " does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error updating player: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Player player = session.get(Player.class, id);
            if (player != null) {
                session.delete(player);
                transaction.commit();
            } else {
                System.out.println("Player with ID " + id + " does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting player: " + e.getMessage());
        }
    }
}
