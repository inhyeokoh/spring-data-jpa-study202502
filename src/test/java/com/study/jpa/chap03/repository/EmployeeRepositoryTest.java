package com.study.jpa.chap03.repository;

import com.study.jpa.chap03.entity.Department;
import com.study.jpa.chap03.entity.Employee;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeInsert() {

        Department d1 = Department.builder()
                .name("영업부")
                .build();
        Department d2 = Department.builder()
                .name("개발부")
                .build();

        Department saved1 = departmentRepository.save(d1);
        Department saved2 = departmentRepository.save(d2);

        Employee e1 = Employee.builder()
                .name("라이옹")
                .department(saved1)
                .build();
        Employee e2 = Employee.builder()
                .name("어피치")
                .department(saved1)
                .build();
        Employee e3 = Employee.builder()
                .name("프로도")
                .department(saved2)
                .build();
        Employee e4 = Employee.builder()
                .name("네오")
                .department(saved2)
                .build();

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);


        /*
            SELECT 쿼리없이 정상적으로 조회에 성공하는 이유는
            JPA는 하나의 트랜잭션안에서 INSERT와 SELECT가 동시에 일어나면
            기존에 INSERT한 내용을 메모리에 영속성 컨텍스트라는 공간에 저장해놓고
            캐싱한다.

         */

        em.flush();
        em.clear();

    }

    @Test
    @DisplayName("특정 사원의 정보를 조회하면 부서정보도 함께 조회된다.")
    void findTest() {
        //given
        Long id = 2L;
        //when
        Employee foundEmp = employeeRepository.findById(id).orElseThrow();
        //then
        System.out.println("foundEmp = " + foundEmp);
        System.out.println("foundEmp.getDepartment() = " + foundEmp.getDepartment());

    }

    @Test
    @DisplayName("특정 부서를 조회하면 해당 소속부서원들이 함께 조회된다.")
    void findDeptTest() {
        //given
        Long deptId = 1L;
        //when
        Department foundDept = departmentRepository.findById(deptId).orElseThrow();
        //then
        System.out.println("\n\n====== 부서 정보 ======");
        System.out.println("foundDept = " + foundDept);
        List<Employee> employees = foundDept.getEmployees();
        System.out.println("employees = " + employees);
    }


    @Test
    @DisplayName("양방향 매핑에서 데이터 수정할 때 생기는 문제")
    void changeTest() {
        //given

        // 3번 사원의 부서를 2번부서에서 1번 부서로 수정

        // 3번 사원 조회
        Employee foundEmp = employeeRepository.findById(3L).orElseThrow();

        // 1번 부서를 조회
        Department newDept = departmentRepository.findById(1L).orElseThrow();

        //when
        // 수정 진행
        foundEmp.setDepartment(newDept);

        //then
        System.out.println("\n\nfoundEmp = " + foundEmp);
        System.out.println("foundEmp.getDepartment = " + foundEmp.getDepartment());
    }



}