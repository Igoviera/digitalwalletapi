package com.digitalwalletapi.service;

import com.digitalwalletapi.model.User;

public interface UserService {
    User create(User user);

    User getById(Long userId);
}
