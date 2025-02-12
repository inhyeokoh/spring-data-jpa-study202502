package com.study.jpa.chap05.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdolResponse {

    private String groupName;
    private List<IdolDetail> idols;
    private List<AlbumDetail> albums;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IdolDetail {
        private String idolName;
        private int age;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AlbumDetail {
        private String albumName;
        private int releaseYear;
    }

    /*
        [
            {
                groupName: '아이브',
                idols: [
                    {
                        idolName: '장원영',
                        age: 20
                    }
                ],
                albums: [
                    {
                        albumName: 'Love dive',
                        releaseYear: 2022
                    }
                ]
            }
        ]

     */
}
