package com.example.cardmanagementsystem.service.impl;

import com.example.cardmanagementsystem.model.Currency;
import com.example.cardmanagementsystem.repository.CurrencyRepository;
import com.example.cardmanagementsystem.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;


    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> listAll() {
        return this.currencyRepository.findAll();
    }

    @Override
    public Currency create(Currency currency) {
        return currencyRepository.save(currency);
    }
}
