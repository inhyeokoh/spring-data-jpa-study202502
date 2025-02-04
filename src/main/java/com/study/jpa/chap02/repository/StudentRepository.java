package com.study.jpa.chap02.repository;

import com.study.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    // JPQL 사용하기
    // 도시이름으로 학생정보 조회
    @Query("SELECT st FROM Student st WHERE st.city = ?1")
    List<Student> getStudentsByCity(String city);

    // 순수 SQL 사용하기
    // 이름 또는 도시로 학생정보 조회
    @Query(value = """
            SELECT * 
            FROM tbl_student
            WHERE stu_name = ?1 
                OR city = ?2
            """, nativeQuery = true)
    List<Student> getStudentsByName(String name, String city);

}
