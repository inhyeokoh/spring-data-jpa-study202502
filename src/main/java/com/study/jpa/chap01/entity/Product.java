package com.study.jpa.chap01.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity // 이 클래스는 데이터베이스와 1대1로 대응되는 엔터티 클래스임
@Table(name = "tbl_product") // 데이터베이스 테이블명 지정
public class Product {

    // PK 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "prod_id")
    private Long id;

    @Column(name = "prod_nm", nullable = false, length = 30)
    private String name; // 상품명

    @Column(name = "prod_price", nullable = true)
    private int price; // 상품 가격

    @CreationTimestamp   // INSERT시 자동으로 시간 저장
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdAt; // 상품 등록 시간

    @UpdateTimestamp   // UPDATE문 실행 시 자동으로 시간 수정
    private LocalDateTime updatedAt; // 상품 정보 수정 시간

    // 열거형 데이터는 따로 설정을 안하면 숫자로 저장됨
    @Enumerated(EnumType.STRING)
    private Category category; // 상품 카테고리

    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }

}
