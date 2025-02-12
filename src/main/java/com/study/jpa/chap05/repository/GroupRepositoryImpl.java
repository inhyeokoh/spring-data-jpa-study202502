package com.study.jpa.chap05.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.chap05.dto.GroupAverageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.jpa.chap05.entity.QIdol.idol;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    // querydsl을 사용하게 해주는 의존객체
    private final JPAQueryFactory factory;

    // jdbc를 위한 의존객체
    private final JdbcTemplate template;

    // JDBC로 구현
    @Override
    public List<GroupAverageResponse> groupAverage() {
        String sql = """
                SELECT 
                    G.group_name AS groupName
                    , AVG(I.age) AS averageAge
                FROM tbl_idol I
                JOIN tbl_group G
                ON I.group_id = G.group_id
                GROUP BY G.group_id
                """;
        return template.query(sql,
                (rs, n)
                        -> GroupAverageResponse.builder()
                        .groupName(rs.getString("groupName"))
                        .averageAge(rs.getDouble("averageAge"))
                        .build());
    }

    // querydsl로 구현
//    @Override
//    public List<GroupAverageResponse> groupAverage() {
//        return factory
//                .select(idol.group.groupName, idol.age.avg())
//                .from(idol)
//                .groupBy(idol.group)
//                .fetch()
//                .stream()
//                .map(GroupAverageResponse::from)
//                .collect(Collectors.toList())
//                ;
//    }
}
