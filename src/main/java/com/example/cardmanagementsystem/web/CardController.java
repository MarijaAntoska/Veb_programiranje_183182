package com.example.cardmanagementsystem.web;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.Transactions;
import com.example.cardmanagementsystem.model.User;
import com.example.cardmanagementsystem.model.enumeration.Brand;
import com.example.cardmanagementsystem.model.enumeration.CardType;
import com.example.cardmanagementsystem.model.enumeration.Status;
import com.example.cardmanagementsystem.service.CardService;
import com.example.cardmanagementsystem.service.TransactionService;
import com.example.cardmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CardController {
    private final CardService cardService;
    private final UserService userService;
    private final TransactionService transactionService;

    public CardController(CardService cardService, UserService userService, TransactionService transactionService) {
        this.cardService = cardService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/cards")
    public String showCards(@RequestParam(required = false) CardType type,
                            Model model) {
        List<Card> card;
        if(type != null)
        {
            card = this.cardService.findAllByTypes(type);
        }
        else{
            card=this.cardService.listAll();
        }

        model.addAttribute("card", card);
        model.addAttribute("type",CardType.values());
        return "cards.html";
    }

    @GetMapping("/cards/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Card card = cardService.findById(id);
        List<User> users = this.userService.listAll();

        model.addAttribute("card", card);
        model.addAttribute("status", Status.values());
        model.addAttribute("hidden", "hidden");
        model.addAttribute("users", users);
        model.addAttribute("activeuser", card.getUser().getId());

        return "formCard.html";
    }

    @GetMapping("/card/add")
    public String addCard(Model model) {
        List<User> users = this.userService.listAll();
        model.addAttribute("brand", Brand.values());
        model.addAttribute("status", Status.values());
        model.addAttribute("type", CardType.values());
        model.addAttribute("users", users);
        return "formCard.html";
    }
    @GetMapping("/cards/{id}")
    public String findByUserId(@PathVariable Long id,
                               @RequestParam(required = false) CardType type,
                               Model model) {

        User user = this.userService.findById(id);
        List<Card> card;
        if(type == null) {
            card = this.cardService.findCardsByUser(user);
        }
        else{
            card = this.cardService.findCardsByUserAndTypes(user,type);
        }
        if(!card.isEmpty()){
            model.addAttribute("card",card);
        }
        model.addAttribute("type",CardType.values());

        return "/cards.html";
    }

    @PostMapping("/card/save")
    public String edit(Card card) {
        if (card.getId() == null) {
            cardService.create(card);
        } else {
            cardService.edit(card);
        }
        return "redirect:/cards";
    }


}
