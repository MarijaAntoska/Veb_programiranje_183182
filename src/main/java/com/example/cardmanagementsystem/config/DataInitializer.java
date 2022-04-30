package com.example.cardmanagementsystem.config;

import com.example.cardmanagementsystem.model.*;
import com.example.cardmanagementsystem.model.enumeration.Brand;
import com.example.cardmanagementsystem.model.enumeration.CardType;
import com.example.cardmanagementsystem.model.enumeration.Role;
import com.example.cardmanagementsystem.model.enumeration.Status;
import com.example.cardmanagementsystem.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer {
    private final UserService userService;
    private final CompanyService companyService;
    private final CardService cardService;
    private final CurrencyService currencyService;
    private final TransactionService transactionService;

    public DataInitializer(UserService userService, CompanyService companyService, CardService cardService, CurrencyService currencyService, TransactionService transactionService) {
        this.userService = userService;
        this.companyService = companyService;
        this.cardService = cardService;
        this.currencyService = currencyService;
        this.transactionService = transactionService;
    }

    @PostConstruct
    public void initData() {

        for (int i = 0; i < 5; i++) {
            Company company = Company.builder().name("Company" + i).build();
            this.companyService.create(company);


            User user = User.builder().name("User" + i).surname("Surname" + i)
                    .address("Test").card(null).company(null)
                    .email("test@hotmail.com").embg("123123")
                    .password("pass").role(Role.ROLE_ADMIN)
                    .salary(123).username("Marija"+i)
                    .isAccountNonLocked(true).isAccountNonExpired(true).isEnabled(true)
                    .isCredentialsNonExpired(true)
                    .company(company)
                    .dateOfBirth(generateDate()).build();
            this.userService.create(user);

            Card card = Card.builder().transactions(null)
                    .user(user)
                    .limits(0)
                    .interestRate(0)
                    .amount(80000.0)
                    .types(CardType.DEBIT)
                    .status(Status.ACTIVE)
                    .brand(Brand.Master_Card)
                    .build();
            this.cardService.create(card);


        }
        Currency currency1= Currency.builder().id("807").name("MKD").build();
        Currency currency2= Currency.builder().id("978").name("EUR").build();
        Currency currency3= Currency.builder().id("840").name("USD").build();

        this.currencyService.create(currency1);
        this.currencyService.create(currency2);
        this.currencyService.create(currency3);

        List<Card> cardList = this.cardService.listAll();
        Random random = new Random();
        Random suma = new Random();
        for(int i=0;i<15;i++) {
            int suma1 = random.nextInt(5000);
            int ranExternalID = random.nextInt(1000000);
            Random RandomCard = new Random();
            String pom = "200";
                Card tempCard = cardList.get(RandomCard.nextInt(4));
            if (i % 3 == 0) {
                pom = "100";
                Transactions transactions = Transactions.builder().account(pom)
                        .currency(currency1)
                        .date(generateDate())
                        .num1(suma1)
                        .num2(0)
                        .num3(suma1)
                        .num4(0)
                        .externalId((long) ranExternalID)
                        .cardid(tempCard)
                        .build();

                this.transactionService.create(transactions);

                tempCard.setAmount(tempCard.getAmount() - suma1);
                this.cardService.edit(tempCard);
            }
            else{
                Transactions transactions = Transactions.builder().account(pom)
                        .currency(currency1)
                        .date(generateDate())
                        .num1(0)
                        .num2(suma1)
                        .num3(0)
                        .num4(suma1)
                        .externalId((long) ranExternalID)
                        .cardid(tempCard)
                        .build();

                this.transactionService.create(transactions);

                tempCard.setAmount(tempCard.getAmount() - suma1);
                this.cardService.edit(tempCard);
            }
        }
    }

    private LocalDate generateDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1940, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2022, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);

        return randomBirthDate;
    }

}
