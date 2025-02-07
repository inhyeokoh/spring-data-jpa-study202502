package com.study.jpa.chap05.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.chap05.entity.Group;
import com.study.jpa.chap05.entity.Idol;
import com.study.jpa.chap05.entity.QIdol;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryDslBasicTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    EntityManager em; // JPA를 제어하는 객체

    @Autowired
    JdbcTemplate template; // JDBC를 제어하는 객체

//    @Autowired
    JPAQueryFactory factory; // QueryDsl을 제어하는 객체

    @BeforeEach
    void setUp() {

        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);

    }

    @Test
    @DisplayName("JPQL로 특정 이름의 아이돌 조회하기")
    void jpqlTest() {
        //given
        String jpql = "SELECT i FROM Idol i WHERE i.idolName = ?1";

        //when
        Idol foundIdol = em.createQuery(jpql, Idol.class)
                .setParameter(1, "가을")
                .getSingleResult();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());

    }

    @Test
    @DisplayName("SQL로 특정 이름의 아이돌 조회하기")
    void nativeSqlTest() {
        //given
        String sql = "SELECT * FROM tbl_idol WHERE idol_name = ?1";

        //when
        Idol foundIdol = (Idol) em.createNativeQuery(sql, Idol.class)
                        .setParameter(1, "리즈")
                        .getSingleResult();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());

    }

    @Test
    @DisplayName("JDBC로 특정 이름의 아이돌 조회하기")
    void jdbcTest() {
        //given
        String sql = "SELECT * FROM tbl_idol WHERE idol_name = ?";

        //when
        Idol foundIdol = template.queryForObject(sql, (rs, n) -> new Idol(
                rs.getLong("idol_id")
                , rs.getString("idol_name")
                , rs.getInt("age")
                , null
        ), "가을");

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
//        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());

    }


    @Test
    @DisplayName("QueryDsl로 특정 이름의 아이돌 조회하기")
    void queryDslTest() {
        //given
        factory = new JPAQueryFactory(em);

        QIdol idol = QIdol.idol;

        //when
        Idol foundIdol = factory
                .selectFrom(idol)
                .where(idol.idolName.eq("리즈"))
                .fetchOne();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }


}