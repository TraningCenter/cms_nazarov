package postmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import postmanager.model.entities.User;
import postmanager.model.service.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/user")
    public List<User> getUsers(){
        return new LinkedList<>();
    }
}
