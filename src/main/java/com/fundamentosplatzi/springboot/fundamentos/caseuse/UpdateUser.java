package com.fundamentosplatzi.springboot.fundamentos.caseuse;


import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {

    private UserService userService;

    public UpdateUser(UserService userService) {
        this.userService = userService;
    }

    public User updateUser(User newUser, Long id){
        return userService.updateUser(newUser, id);
    }
}
