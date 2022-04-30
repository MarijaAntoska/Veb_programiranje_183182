package com.example.cardmanagementsystem.service.impl;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.Company;
import com.example.cardmanagementsystem.model.Transactions;
import com.example.cardmanagementsystem.repository.TransactionsRepository;
import com.example.cardmanagementsystem.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionsRepository transactionsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TransactionServiceImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }


    @Override
    public List<Transactions> listAll() {
        return this.transactionsRepository.findAll();
    }

    @Override
    public List<Transactions> findTransactionsByCardid(Card card) {
        return this.transactionsRepository.findTransactionsByCardid(card);
    }

    @Override
    public Transactions create(Transactions transactions) {
        entityManager.detach(transactions.getCardid());
        return transactionsRepository.save(transactions);
    }

    @Override
    public List<Transactions> findAllByAccountContaining(String account) {
        return this.transactionsRepository.findAllByAccountContaining(account);
    }

    @Override
    public List<Transactions> findTransactionsByCardidAndAccountContaining(Card card, String account) {
        return this.transactionsRepository.findTransactionsByCardidAndAccountContaining(card,account);
    }

    @Override
    public List<Transactions> findTransactionsByDateAfter(LocalDate date) {
        return this.transactionsRepository.findTransactionsByDateAfter(date);
    }

    @Override
    public List<Transactions> findTransactionsByCardidAndAccountContainingAndDateAfter(Card card, String account, LocalDate date) {
        return this.transactionsRepository.
                findTransactionsByCardidAndAccountContainingAndDateAfter(card,account,date);
    }

    @Override
    public List<Transactions> findTransactionsByCardidAndDateAfter(Card card, LocalDate date) {
        return this.transactionsRepository.
                findTransactionsByCardidAndDateAfter(card,date);
    }

    @Override
    public List<Transactions> findTransactionsByAccountAndDateAfter(String account, LocalDate date) {
        return this.transactionsRepository.findTransactionsByAccountAndDateAfter(account,date);
    }
}
