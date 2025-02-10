package com.study.jpa.chap05.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_idol")
@Setter
@Getter
@ToString(exclude = "group")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Idol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idol_id")
    private Long id;

    private String idolName;

    private int age;

    private String gender; // 성별 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Idol(String idolName, int age, Group group) {
        this(idolName, age, null, group);
    }

    public Idol(String idolName, int age, String gender, Group group) {
        this.idolName = idolName;
        this.age = age;
        this.gender = gender;
        if (group != null) {
            changeGroup(group);
        }
    }

    public void changeGroup(Group group) {
        this.group = group;
        group.getIdols().add(this);
    }
}
