package com.example.shoppingapp.service;

import com.example.shoppingapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

public User getUserById(Long id){
    try {
        System.out.println("Istifadecini axtariram, 3 saniye davam edecek");
        Thread.sleep(3000);
    }catch (InterruptedException e){
        e.printStackTrace();
    }

        return new User(id, "User_" + id);
    }
}

