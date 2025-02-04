package com.study.jpa.chap02.repository;

import com.study.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, String> {

    // Query method : 메서드에 특별한 이름규칙을 사용하여 SQL을 생성
    List<Student> findByName(String name);

    // where city = ? and major = ?
    List<Student> findByCityAndMajor(String city, String major);

    // where major like '%major%'
    List<Student> findByMajorContaining(String major);

    // where major like 'major%'
    List<Student> findByMajorStartingWith(String major);

    // where major like '%major'
    List<Student> findByMajorEndingWith(String major);

    // where age <= ?
//    List<Student> findByAgeLessThanEqual(int age);
}
