package com.study.jpa.chap03.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"employees"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

// 부서 1
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id; // 부서번호

    @Column(name = "dept_name", nullable = false)
    private String name; // 부서명

    // 양방향 매핑
    /*
    - 양방향 매핑은 데이터베이스와 달리 객체지향 시스템에서 가능한 방법으로
        1대N관계에서 1쪽에 N데이터를 포함시킬 수 있는 방법이다.

        - 양방향 매핑에서 1쪽은 상대방 엔터티 갱신에 관여 할 수 없고
           (리스트에서 사원을 지운다고 실제 디비에서 사원이 삭제되지는 않는다는 말)
           단순히 읽기전용 (조회전용)으로만 사용하는 것이다.

        - mappedBy에는 상대방 엔터티에 @ManyToOne에 대응되는 필드명을 꼭 적어야 함

        - CascadeType
          * PERSIST : 부모가 갱신되면 자식도 같이 갱신된다.
          - 부모의 리스트에 자식을 추가하거나 제거하면
            데이터베이스에도 반영된다.

          * REMOVE : 부모가 제거되면 자식도 같이 제거된다.
          - ON DELETE CASCADE

          * ALL : 위의 내용을 전부 포함

        - orphanRemoval : 고아 객체 삭제 - 부모와의 연결이 끊어진 자식객체를 데이터베이스에서 삭제
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();


    // 리스트에 사원 추가 편의 메서드
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    // 리스트에 사원 제거 편의 메서드
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }

}
