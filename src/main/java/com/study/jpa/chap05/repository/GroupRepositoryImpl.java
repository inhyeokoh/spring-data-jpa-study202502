package com.study.jpa.chap05.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.chap05.dto.GroupAverageResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.jpa.chap05.entity.QIdol.idol;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    // querydsl을 사용하게 해주는 의존객체
    private final JPAQueryFactory factory;

    // querydsl로 구현
    @Override
    public List<GroupAverageResponse> groupAverage() {
        return factory
                .select(idol.group.groupName, idol.age.avg())
                .from(idol)
                .groupBy(idol.group)
                .fetch()
                .stream()
                .map(GroupAverageResponse::from)
                .collect(Collectors.toList())
                ;
    }
}
