package com.study.jpa.chap02.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @Column(name = "stu_id")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    @Column(name = "stu_name", nullable = false)
    private String name;

    private String city;

    private String major;
}
