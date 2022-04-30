package com.example.cardmanagementsystem.web;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.Company;
import com.example.cardmanagementsystem.model.User;
/*import com.example.cardmanagementsystem.service.CardService;
import com.example.cardmanagementsystem.service.UserService;*/
import com.example.cardmanagementsystem.model.enumeration.Role;
import com.example.cardmanagementsystem.model.enumeration.Status;
import com.example.cardmanagementsystem.model.exceptions.UsernameAlreadyExistsException;
import com.example.cardmanagementsystem.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final CardService cardService;
    private final CompanyService companyService;
    private final CurrencyService currencyService;
    private final TransactionService transactionService;
    private final UserService userService;

    public UserController(CardService cardService, CompanyService companyService, CurrencyService currencyService, TransactionService transactionService, UserService userService) {
        this.cardService = cardService;
        this.companyService = companyService;
        this.currencyService = currencyService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {

        List<User> users = this.userService.listAll();
        model.addAttribute("users", users);

        return "users.html";
    }

    @GetMapping("/users/{id}")
    public String searchUserById(@PathVariable Long id, Model model) {

        User user = this.userService.findById(id);
        model.addAttribute("users", user);

        return "users.html";
    }

    @GetMapping("/users/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        List<Company> companies = companyService.listAll();
        String pom = "save";
        model.addAttribute("user", user);
        model.addAttribute("companies", companies);
        model.addAttribute("role", Role.values());
        model.addAttribute("pom",pom);

        return "formUser.html";
    }


    @GetMapping("/users/create")
    public String create(Model model) {
        List<Company> companies = companyService.listAll();
        String pom = "create";

        model.addAttribute("companies", companies);
        model.addAttribute("role", Role.values());
        model.addAttribute("pom",pom);

        return "formUser.html";
    }

    @PostMapping("/users/create")
    public String create(User user) throws Exception {
        Optional<User> u1 = userService.findByUsername(user.getUsername());
        Optional<User> u2 = userService.findByUsername(user.getEmail());

        if (u1.isEmpty() && u2.isEmpty()) {
            userService.create(user);
            return "redirect:/";

        } else {
            return "redirect:/access_denied";
        }
    }
    @PostMapping("/users/save")
    public String edit(User user) {
        userService.edit(user);
        return "redirect:/users";
    }
}
