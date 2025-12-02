package com.example.shoppingapp.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {

private List<String> cartItems = new ArrayList<>();
public ShoppingCart(){
    System.out.println(">>> YENİ BİR SEPET OLUŞTURULDU! HashCode: " + this.hashCode());

}

    public void addCart(String item){
        cartItems.add(item);
    }

    public void removeCart (){
        cartItems.clear();

    }
    public List<String> getCartItems(){
        return cartItems;
    }

}
