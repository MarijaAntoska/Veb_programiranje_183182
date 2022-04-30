package com.example.cardmanagementsystem.web;

import com.example.cardmanagementsystem.model.User;
import com.example.cardmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String homePage(Principal principal, Model model){
        if(principal !=null){
        User user = this.userService.findByName(principal.getName());
        model.addAttribute("user",user);}
        return "homePage.html";
    }

    @GetMapping("/access_denied")
    public String accessDenied(Model model){

        return "accessdenied.html";
    }

}
