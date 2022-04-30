package com.example.cardmanagementsystem.web;

import com.example.cardmanagementsystem.model.Card;
import com.example.cardmanagementsystem.model.Currency;
import com.example.cardmanagementsystem.model.Transactions;
import com.example.cardmanagementsystem.model.enumeration.CardType;
import com.example.cardmanagementsystem.model.enumeration.Status;
import com.example.cardmanagementsystem.service.CardService;
import com.example.cardmanagementsystem.service.TransactionService;
import org.hibernate.Transaction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class TransactionsController {
    private final TransactionService transactionService;
    private final CardService cardService;

    public TransactionsController(TransactionService transactionService, CardService cardService) {
        this.transactionService = transactionService;
        this.cardService = cardService;
    }

    @GetMapping("/transactions")
    public String allTransactions(@RequestParam(required = false) String accountNumber,
                                  @RequestParam(required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd")
                                  LocalDate dateHigher,
                                  Model model) {
        List<Transactions> transactions;
        if (accountNumber != null && !accountNumber.isEmpty() && dateHigher != null) {
            transactions = this.transactionService.findTransactionsByAccountAndDateAfter(accountNumber, dateHigher);
        } else if (dateHigher != null) {
            transactions = this.transactionService.findTransactionsByDateAfter(dateHigher);
        } else if (accountNumber != null && !accountNumber.isEmpty()) {
            transactions = this.transactionService.findAllByAccountContaining(accountNumber);
        } else {
            transactions = this.transactionService.listAll();
        }

        model.addAttribute("transactions", transactions);


        return "transactions.html";
    }

    @GetMapping("/card/{id}/transaction")
    public String showTransaction(@PathVariable Long id,
                                  @RequestParam(required = false) String accountNumber,
                                  @RequestParam(required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd")
                                  LocalDate dateHigher,
                                  Model model) {
        Card card = this.cardService.findById(id);
        List<Transactions> transactions;


        if (accountNumber != null && !accountNumber.isEmpty() && dateHigher != null) {
            transactions = this.transactionService.findTransactionsByCardidAndAccountContainingAndDateAfter(card, accountNumber, dateHigher);
        } else if (accountNumber != null && !accountNumber.isEmpty()) {
            transactions = this.transactionService.findTransactionsByCardidAndAccountContaining(card, accountNumber);
        } else if (dateHigher != null) {
            transactions = this.transactionService.findTransactionsByCardidAndDateAfter(card, dateHigher);
        } else {
            transactions = this.transactionService.findTransactionsByCardid(card);
        }

        if (!transactions.isEmpty()) {
            model.addAttribute("transactions", transactions);
        }
        return "transactions.html";
    }

    @GetMapping("/{id}/transaction/add")
    public String addTransaction(@PathVariable String id, Model model) {
        Card c = cardService.findById(Long.valueOf(id));
        List<Card> cards = cardService.listAll();
        cards.remove(c);

        model.addAttribute("cards", cards);
        model.addAttribute("amount", c.getAmount());
        model.addAttribute("status", c.getStatus());
        return "addTransaction.html";
    }

    @Transactional
    @PostMapping("/{id}/transaction/add")
    public String addTransaction(@PathVariable String id, String cardD, Card card, Double ammount) {
        Card sender = this.cardService.findById(Long.valueOf(cardD));
        Card c = this.cardService.findById(Long.valueOf(id));

        if (!c.getStatus().equals(Status.ACTIVE)) {
            return "redirect:/access_denied";
        }

        if ((c.getAmount() - ammount) < c.getLimits()) {
            return "redirect:/access_denied";
        }
        Currency currency = Currency.builder().id("807").name("MKD").build();

        Transactions transactions = Transactions.builder().account("100")
                .currency(currency)
                .date(LocalDate.now())
                .num1(ammount.intValue())
                .num2(0)
                .num3(ammount.intValue())
                .num4(0)
                .cardid(card)
                .build();

        Transactions transaction2 = Transactions.builder().account("200")
                .currency(currency)
                .date(LocalDate.now())
                .num1(0)
                .num2(ammount.intValue())
                .num3(0)
                .num4(ammount.intValue())
                .cardid(sender)
                .build();

        card.setAmount(card.getAmount() - ammount.intValue());
        sender.setAmount(sender.getAmount() + ammount.intValue());

        transactionService.create(transactions);
        transactionService.create(transaction2);
        cardService.edit(card);
        cardService.edit(sender);

        return "redirect:/";
    }

    @GetMapping("/calculate")
    public String calculate2() {
        return "/cards.html";
    }

    @PostMapping("/calculate")
    public String calculate() {

        List<Card> cards = this.cardService.listAll();
        double interestRate = 0.0;
        Random random = new Random();

        for (int i = 0; i < cards.size(); i++) {
            interestRate = 0.0;
            if (cards.get(i).getTypes().equals(CardType.CREDIT)) {
                if (cards.get(i).getAmount() < 0) {
                    int ranExternalID = random.nextInt(1000000);
                    interestRate = ((cards.get(i).getAmount()/100) * cards.get(i).getInterestRate()) * -1;
                    Currency currency1 = Currency.builder().id("807").name("MKD").build();
                    Transactions transactions = Transactions.builder().account("110")
                            .currency(currency1)
                            .date(LocalDate.now())
                            .num1(interestRate)
                            .num2(0)
                            .num3(interestRate)
                            .num4(0)
                            .externalId(Long.valueOf(ranExternalID))
                            .cardid(cards.get(i))
                            .build();
                    this.transactionService.create(transactions);
                    cards.get(i).setAmount(cards.get(i).getAmount() - interestRate);
                    this.cardService.edit(cards.get(i));
                }
            }
        }
        return "redirect:/cards";
    }
}
