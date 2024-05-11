package com.ecomarket.controller;

import com.ecomarket.entity.CartItem;
import com.ecomarket.service.OrderService;
import com.ecomarket.service.WorkshopService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;


@Controller
public class ShopController
{
    @Autowired
    WorkshopService workshopService;
    @Autowired
    OrderService orderService;
    @GetMapping("/getcookies")
    public String cookieGet(@RequestParam("cookies") String param, RedirectAttributes redirectAttributes)
    {
        if (workshopService.convertCookies(param, redirectAttributes))
        {
            return "redirect:/order";
        }
        return "error";
    }
    @GetMapping("/getproducts")
    public String productsGet(@RequestParam("products") String param, RedirectAttributes redirectAttributes)
    {
        if (workshopService.convertCookies(param, redirectAttributes))
        {
            return "redirect:/order/add";
        }
        return "error";
    }
    @GetMapping("/catalog")
    public String catalogGet(Model model)
    {
        var workshops = workshopService.getWorkshops();
        model.addAttribute("workshops", workshops);
        return "catalog";
    }
    @GetMapping("/order/add")
    public String orderPost(Principal principal, @ModelAttribute("items") CartItem[] items, HttpServletResponse response)
    {
        if (orderService.addOrder(Arrays.stream(items).toList(), principal.getName()))
        {
            var deleteCookie = new Cookie("cartItems", null);
            deleteCookie.setMaxAge(0);
            response.addCookie(deleteCookie);
            return "redirect:/account";
        }
        return "redirect:/error";
    }
    @GetMapping("/order")
    public String orderGet(@ModelAttribute("items") CartItem[] items, Model model)
    {
        var products = workshopService.convertItemsToProduct(items);
        model.addAttribute("products", products);
        model.addAttribute("sum", workshopService.getSum(products));
        return "order";
    }
}