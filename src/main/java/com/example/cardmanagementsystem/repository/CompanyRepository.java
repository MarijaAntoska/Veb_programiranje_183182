package com.example.cardmanagementsystem.repository;

import com.example.cardmanagementsystem.model.Company;
import com.example.cardmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
