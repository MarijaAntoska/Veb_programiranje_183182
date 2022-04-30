package com.example.cardmanagementsystem.repository;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.Transactions;
import com.example.cardmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findTransactionsByCardid(Card card);
    List<Transactions> findAllByAccountContaining(String account);
    List<Transactions> findTransactionsByCardidAndAccountContaining(Card card, String account);

    List<Transactions> findTransactionsByDateAfter(LocalDate date);
    List<Transactions> findTransactionsByCardidAndAccountContainingAndDateAfter(Card card, String account,LocalDate date);

    List<Transactions> findTransactionsByCardidAndDateAfter(Card card, LocalDate date);
    List<Transactions> findTransactionsByAccountAndDateAfter(String account,LocalDate date);
}
