package com.example.shoppingapp.controller;

import com.example.shoppingapp.shoppingcart.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ShoppingCart cart;
    public CartController(ShoppingCart cart){
        this.cart = cart;
    }
@GetMapping("/add")
public void addItems (String item){
        cart.addCart(item);
}

@GetMapping("/get")
    public List<String> items (){
        return cart.getCartItems();
}
@GetMapping("/remove")
    public void removeCart(){
        cart.removeCart();
}
}
