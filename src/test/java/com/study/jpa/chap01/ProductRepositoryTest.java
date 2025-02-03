package com.study.jpa.chap01;

import com.study.jpa.chap01.entity.Product;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.study.jpa.chap01.entity.Product.Category.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)

//@DataJpaTest   // 기본적으로 JPA에 필요한 의존객체만 가볍게 세팅, rollback기본
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager em;

    // 각 테스트 실행 전에 자동 수행될 코드
    @BeforeEach
    void insertBeforeTest() {

        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(2000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("주먹밥")
                .category(FOOD)
                .price(1500)
                .build();

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);

        em.flush();
        em.clear();

    }


    @Test
    @DisplayName("상품을 데이터베이스에 저장한다.")
    void saveTest() {
        //given
        Product newProduct = Product.builder()
                .name("떡볶이")
                .price(4000)
                .category(FOOD)
                .build();
        //when
        Product saved = productRepository.save(newProduct);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("떡볶이");
    }

    @Test
    @DisplayName("1번 상품을 삭제한다")
    void deleteTest() {
        //given
        Long id = 1L;
        //when
        productRepository.deleteById(id);

        //then
    }


    @Test
    @DisplayName("3번 상품을 단일조회하면 그 상품명이 구두이다.")
    void findOneTest() {
        //given
        Long id = 3L;
        //when
        Product product = productRepository.findById(id)
                .orElseThrow();
        //then
        System.out.println("\n\nproduct = " + product + "\n\n");

        assertThat(product.getName()).isEqualTo("구두");
    }

    @Test
    @DisplayName("상품을 전체조회하면 총 4개의 상품이 조회된다.")
    void findAllTest() {
        //given

        //when
        List<Product> products = productRepository.findAll();
        //then
        products.forEach(System.out::println);

        assertThat(products.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("2번 상품의 이름과 가격을 수정한다.")
    void updateTest() {
        //given
        Long id = 2L;
        String newName = "청소기";
        int newPrice = 120000;
        Product.Category newCategory = ELECTRONIC;

        //when
        /*
            JPA에서는 수정메서드를 따로 제공하지 않습니다.
            단일 조회를 수행한 후 setter를 통해 값을 변경하고
            다시 save를 하면 INSERT문 대신에 UPDATE문이 나갑니다.
         */
        Product foundProduct = productRepository.findById(id).orElseThrow();
        foundProduct.changeProduct(newName, newPrice, newCategory);

        productRepository.save(foundProduct);

        //then
    }


}