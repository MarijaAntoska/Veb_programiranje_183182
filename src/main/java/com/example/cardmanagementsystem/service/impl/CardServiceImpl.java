package com.example.cardmanagementsystem.service.impl;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.User;
import com.example.cardmanagementsystem.model.enumeration.Brand;
import com.example.cardmanagementsystem.model.enumeration.CardType;
import com.example.cardmanagementsystem.model.exceptions.InvalidCardIdException;
import com.example.cardmanagementsystem.repository.CardRepository;
import com.example.cardmanagementsystem.service.CardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> listAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id).orElseThrow(InvalidCardIdException::new);
    }

    @Override
    public Card create(Card card) {

        generateId(card);
        generateCVC(card);
        generateDate(card);

        return cardRepository.save(card);
    }

    private void generateDate(Card card) {

        LocalDate localDate = LocalDate.now();
        card.setValidThru(localDate.plusYears(5));

    }

    private void generateCVC(Card card) {

        Random random = new Random();
        int id = random.nextInt(999);
        String formatted = String.format("%03d", id);
        card.setCVC(Integer.parseInt(formatted));

    }

    private void generateId(Card card) {

        String id = "";
        if (card.getBrand().equals(Brand.Master_Card)) {
            id += "5120";
        } else if (card.getBrand().equals(Brand.VISA)) {
            id += "4120";
        }
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < 12; i++) {
                Random random = new Random();
                id += Integer.toString(random.nextInt(9));
            }
            if (cardRepository.findById(Long.valueOf(id)).isEmpty()) {
                flag = false;
            }
        }
        card.setId(Long.valueOf(id));

    }

    @Override
    public Card edit(Card c) {

        Card card = this.findById(c.getId());
        card.setStatus(c.getStatus());
        card.setAmount(c.getAmount());
        return this.cardRepository.save(card);

    }

    @Override
    public List<Card> findCardsByUser(User user) {
        return this.cardRepository.findCardsByUser(user);
    }


    @Override
    public List<Card> findAllByTypes(CardType type) {
        return this.cardRepository.findAllByTypes(type);
    }

    @Override
    public List<Card> findCardsByUserAndTypes(User user, CardType types) {
        return cardRepository.findCardsByUserAndTypes(user,types);
    }


}