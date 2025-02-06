package com.study.jpa.chap04.repository;

import com.study.jpa.chap04.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
