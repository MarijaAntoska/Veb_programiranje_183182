package com.example.cardmanagementsystem.service.impl;

import com.example.cardmanagementsystem.model.Company;
import com.example.cardmanagementsystem.repository.CompanyRepository;
import com.example.cardmanagementsystem.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> listAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company create(Company company) {
        return companyRepository.save(company);
    }

}
