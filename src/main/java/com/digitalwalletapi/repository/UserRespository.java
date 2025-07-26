package com.digitalwalletapi.repository;

import com.digitalwalletapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
}
