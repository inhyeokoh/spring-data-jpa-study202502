package com.study.jpa.chap04.repository;

import com.study.jpa.chap04.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
