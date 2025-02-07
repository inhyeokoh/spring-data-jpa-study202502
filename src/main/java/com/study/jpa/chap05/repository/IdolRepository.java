package com.study.jpa.chap05.repository;

import com.study.jpa.chap05.entity.Idol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdolRepository extends JpaRepository<Idol, Long> {


}
