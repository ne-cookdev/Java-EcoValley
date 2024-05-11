package com.ecomarket.controller;

import com.ecomarket.entity.UserDTO;
import com.ecomarket.service.EntryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EntryController
{
    @Autowired
    EntryService entryService;
    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") UserDTO newUser, HttpServletRequest request) throws ServletException
    {
        if (!newUser.getPassword().equals(newUser.getConfirmPassword()))
        {
            return "redirect:/register?error";
        }
        if (entryService.addUser(newUser))
        {
            request.login(newUser.getName(), newUser.getPassword());
            return "redirect:/catalog";
        }
        return "redirect:/register?exists";
    }
    @GetMapping("/register")
    public String registerGet(Model model)
    {
        model.addAttribute("user", new UserDTO());
        return "register";
    }
    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }
}
