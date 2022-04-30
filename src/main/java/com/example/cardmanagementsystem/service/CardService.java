package com.example.cardmanagementsystem.service;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.enumeration.Brand;
import com.example.cardmanagementsystem.model.enumeration.CardType;
import com.example.cardmanagementsystem.model.User;
import com.example.cardmanagementsystem.model.enumeration.Status;

import java.time.LocalDate;
import java.util.List;

public interface CardService {

    List<Card> listAll();
    public Card findById(Long id);
    public Card create(Card card);
    public Card edit(Card card);
    List<Card> findCardsByUser(User user);
    List<Card> findAllByTypes(CardType type);
    List<Card> findCardsByUserAndTypes(User user,CardType types);
}
