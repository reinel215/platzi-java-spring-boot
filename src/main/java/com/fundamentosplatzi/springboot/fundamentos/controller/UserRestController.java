package com.fundamentosplatzi.springboot.fundamentos.controller;

import com.fundamentosplatzi.springboot.fundamentos.caseuse.CreateUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.DeleteUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.UpdateUser;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.hibernate.sql.Update;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserRestController {

    //create, get, delete, update
    private GetUser getUser;
    private CreateUser createUser;
    private DeleteUser deleteUser;
    private UpdateUser updateUser;
    private UserRepository userRepository;

    public UserRestController(GetUser getUser, CreateUser user, DeleteUser deleteUser, UpdateUser updateUser, UserRepository userRepository) {
        this.getUser = getUser;
        this.createUser = user;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    List<User> get(){
        return getUser.getAll();
    }


    @PostMapping("/")
    ResponseEntity<User> newUser(@RequestBody User newUser){
        return new ResponseEntity<User>(createUser.save(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteUser(@PathVariable Long id){
        deleteUser.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return new ResponseEntity<>(updateUser.updateUser(newUser, id), HttpStatus.OK);
    }


    @GetMapping("/pageable")
    List<User> getUserPageable(@RequestParam int page, @RequestParam int size){
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

}
