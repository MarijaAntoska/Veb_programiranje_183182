package com.example.cardmanagementsystem.service;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.Transactions;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    List<Transactions> listAll();
    List<Transactions> findTransactionsByCardid(Card card);
    Transactions create(Transactions transactions);
    List<Transactions> findAllByAccountContaining(String account);
    List<Transactions> findTransactionsByCardidAndAccountContaining(Card card, String account);
    List<Transactions> findTransactionsByDateAfter(LocalDate date);
    List<Transactions> findTransactionsByCardidAndAccountContainingAndDateAfter(Card card, String account,LocalDate date);

    List<Transactions> findTransactionsByCardidAndDateAfter(Card card, LocalDate date);
    List<Transactions> findTransactionsByAccountAndDateAfter(String account,LocalDate date);


}
