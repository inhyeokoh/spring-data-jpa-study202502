package com.study.jpa.chap05.service;

import com.study.jpa.chap05.dto.GroupAverageResponse;
import com.study.jpa.chap05.dto.IdolResponse;
import com.study.jpa.chap05.entity.Album;
import com.study.jpa.chap05.entity.Group;
import com.study.jpa.chap05.entity.Idol;
import com.study.jpa.chap05.repository.GroupRepository;
import com.study.jpa.chap05.repository.IdolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IdolService {

    private final GroupRepository groupRepository;

    // 아이돌 정보 전체조회
    public List<IdolResponse> getIdols() {

        List<Group> groupList = groupRepository.findAll();

        return groupList.stream()
                .map(group -> {
                    List<Idol> idols = group.getIdols();

                    List<IdolResponse.IdolDetail> idolNames = idols.stream()
                            .map((i) -> IdolResponse.IdolDetail.builder()
                                    .idolName(i.getIdolName())
                                    .age(i.getAge())
                                    .build())
                            .collect(Collectors.toList());

                    List<Album> albums = group.getAlbums();

                    List<IdolResponse.AlbumDetail> albumDetails = albums.stream()
                            .map(a -> IdolResponse.AlbumDetail.builder()
                                    .albumName(a.getAlbumName())
                                    .releaseYear(a.getReleaseYear())
                                    .build())
                            .collect(Collectors.toList());

                    return IdolResponse.builder()
                            .groupName(group.getGroupName())
                            .idols(idolNames)
                            .albums(albumDetails)
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 그룹별 평균나이 조회
    public List<GroupAverageResponse> average() {
        return groupRepository.groupAverage();
    }
}
