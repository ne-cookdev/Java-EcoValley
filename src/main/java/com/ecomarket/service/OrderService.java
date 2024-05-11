package com.ecomarket.service;

import com.ecomarket.entity.CartItem;
import com.ecomarket.entity.Order;
import com.ecomarket.entity.Product;
import com.ecomarket.repository.OrderRepository;
import com.ecomarket.repository.ProductRepository;
import com.ecomarket.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderService
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WorkshopRepository workshopRepository;
    @Autowired
    private UserService userService;
    public boolean addOrder(List<CartItem> items, String name)
    {
        try {
            var order = new Order();
            order.setOrderNum(getOrderNum());
            order.setUser(userService.getUserByName(name));
            order.setProducts(getProductList(items));
            orderRepository.save(order);
            userService.addOrder(order, name);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    private List<Product> getProductList(List<CartItem> items)
    {
        var products = new ArrayList<Product>();
        for (var item : items)
        {
            var product = productRepository.getProductsByTitle(item.getProductName());
            products.add(product);

            var workshop = workshopRepository.getWorkshopByProduct(product);
            workshop.decreaseCount(item.getQuantity());
            workshopRepository.save(workshop);
        }
        return products;
    }
    private int getOrderNum()
    {
        var random = new Random();
        return random.nextInt(999999 - 100000 + 1) + 100000;
    }
}
