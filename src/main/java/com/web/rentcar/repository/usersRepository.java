package com.web.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.rentcar.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface usersRepository extends JpaRepository<Users,Long> {
    Users findByEmail(String email);
}
