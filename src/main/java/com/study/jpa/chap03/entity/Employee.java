package com.study.jpa.chap03.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter @Getter
@ToString(exclude = {"department"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

// 사원 N
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 사원번호

    @Column(name = "emp_name", nullable = false)
    private String name; // 사원명

    // 단방향 매핑 - DBMS처럼 한쪽에 상대의 PK를 FK로 갖는형태
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id") // FK 컬럼명
    private Department department;


}







