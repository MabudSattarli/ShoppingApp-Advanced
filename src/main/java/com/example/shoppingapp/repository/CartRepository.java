package com.example.shoppingapp.repository;

import com.example.shoppingapp.shoppingcart.ShoppingCart;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("session")
public class CartRepository {
    private ShoppingCart cart;

    public ShoppingCart getCart(){
        return cart;
    }

}
