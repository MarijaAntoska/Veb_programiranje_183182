package com.example.cardmanagementsystem.web;

import com.example.cardmanagementsystem.model.Company;
import com.example.cardmanagementsystem.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public String showCompanies(Model model){
        List<Company> companies = companyService.listAll();
        model.addAttribute("companies",companies);
        return "companies.html";
    }

}
