package com.study.jpa.chap01;

import com.study.jpa.chap01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA에서 Repository는 기본 CRUD을 포함하고 있으며
// 첫번째 제너릭타입에는 엔터티 클래스 타입, 두번째는 ID의 타입
public interface ProductRepository extends JpaRepository<Product, Long> {

}
