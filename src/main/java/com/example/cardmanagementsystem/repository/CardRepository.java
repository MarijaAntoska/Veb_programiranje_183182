package com.example.cardmanagementsystem.repository;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.User;
import com.example.cardmanagementsystem.model.enumeration.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findCardsByUser(User u);
    List<Card> findAllByTypes(CardType type);
    List<Card> findCardsByUserAndTypes(User u,CardType types);

}
