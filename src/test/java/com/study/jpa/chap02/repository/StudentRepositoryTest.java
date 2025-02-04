package com.study.jpa.chap02.repository;

import com.study.jpa.chap02.entity.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void insertData() {
        Student s1 = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("쿠로미")
                .city("청양군")
                .major("경제학")
                .build();

        Student s2 = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("춘식이")
                .city("서울시")
                .major("컴퓨터공학")
                .build();

        Student s3 = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("어피치")
                .city("제주도")
                .major("화학공학")
                .build();

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }

    @Test
    @DisplayName("이름이 '춘식이'인 학생의 모든 정보를 조회한다.")
    void test() {
        //given
        String name = "춘식이";
        //when
        List<Student> students = studentRepository.findByName(name);
        //then
        assertThat(students.size()).isEqualTo(1);
        assertThat(students.get(0).getName()).isEqualTo(name);

        students.forEach(System.out::println);
    }

    @Test
    @DisplayName("도시명과 전공명으로 조회")
    void findByCityAndMajorTest() {
        //given
        String city = "제주도";
        String major = "화학공학";
        //when
        List<Student> students = studentRepository.findByCityAndMajor(city, major);
        //then
        students.forEach(System.out::println);
    }

    @Test
    @DisplayName("전공에 공학이 포함된 학생들 조회")
    void containingTest() {
        //given
        String major = "공학";
        //when
        List<Student> students = studentRepository.findByMajorContaining(major);

        //then
        students.forEach(System.out::println);
    }



}