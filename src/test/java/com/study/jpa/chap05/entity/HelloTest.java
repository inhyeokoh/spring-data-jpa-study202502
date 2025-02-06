package com.study.jpa.chap05.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HelloTest {

    @Autowired
    EntityManager em;


    @Test
    @DisplayName("querydsl 동작 확인")
    void testExecuteQueryDSL() {
        //given
        Hello hello = new Hello();
        hello.setName("망아지");
        em.persist(hello);

        //when
        JPAQueryFactory query = new JPAQueryFactory(em);
        QHello qHello = QHello.hello;

        Hello result = query
                .selectFrom(qHello)
                .where(qHello.name.eq("망아지"))
                .fetchOne();

        //then
        System.out.println("result = " + result);
        assertEquals(hello, result);
        assertEquals(hello.getId(), result.getId());
    }
}