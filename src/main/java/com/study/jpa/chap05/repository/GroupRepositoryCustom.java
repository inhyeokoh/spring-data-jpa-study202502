package com.study.jpa.chap05.repository;

import com.study.jpa.chap05.dto.GroupAverageResponse;

import java.util.List;

// querydsl, jdbc와 결합하기 위한 추가 인터페이스
public interface GroupRepositoryCustom {

    // 그룹별 평균나이를 조회 - queryDSL로 조회
    List<GroupAverageResponse> groupAverage();
}
