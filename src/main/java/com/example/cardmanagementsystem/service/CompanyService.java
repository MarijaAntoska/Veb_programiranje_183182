package com.example.cardmanagementsystem.service;


import com.example.cardmanagementsystem.model.Company;


import java.util.List;

public interface CompanyService {

    List<Company> listAll();
    public Company create(Company company);



}
