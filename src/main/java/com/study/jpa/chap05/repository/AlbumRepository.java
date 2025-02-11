package com.study.jpa.chap05.repository;

import com.study.jpa.chap05.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
