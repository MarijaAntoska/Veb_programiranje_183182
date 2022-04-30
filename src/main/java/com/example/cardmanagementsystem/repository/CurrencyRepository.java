package com.example.cardmanagementsystem.repository;

import com.example.cardmanagementsystem.model.Currency;
import com.example.cardmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {


}
