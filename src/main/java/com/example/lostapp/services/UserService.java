package com.example.lostapp.services;

import com.example.lostapp.model.User;
import com.example.lostapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;


@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        int strength = 10;
//        BCryptPasswordEncoder bCryptPasswordEncoder =
//                new BCryptPasswordEncoder(strength, new SecureRandom());
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save( user);
    }

    public User login(User user) throws ClassNotFoundException {
        int strength = 10;
//        BCryptPasswordEncoder bCryptPasswordEncoder =
//                new BCryptPasswordEncoder(strength, new SecureRandom());

        User cUser = userRepository.findByUsername(user.getUsername());

        System.out.println(cUser);

        if(Objects.equals(user.getPassword(), cUser.getPassword())){
            System.out.println("munomu");
            System.out.println(cUser);
            return cUser;
        }
        else {
            throw new ClassNotFoundException();
        }
    }
}
