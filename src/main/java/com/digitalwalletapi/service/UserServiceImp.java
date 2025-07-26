package com.digitalwalletapi.service;

import com.digitalwalletapi.model.User;
import com.digitalwalletapi.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRespository userRespository;

    @Override
    public User create(User user) {
        return userRespository.save(user);
    }

    @Override
    public User getById(Long userId) {
        return userRespository.findById(userId).orElseThrow(() -> new RuntimeException("Não encontrado"));
    }
}
