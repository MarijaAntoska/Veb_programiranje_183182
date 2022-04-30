package com.example.cardmanagementsystem.service;

import com.example.cardmanagementsystem.model.Company;
import com.example.cardmanagementsystem.model.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> listAll();
    Currency create(Currency currency);

}
