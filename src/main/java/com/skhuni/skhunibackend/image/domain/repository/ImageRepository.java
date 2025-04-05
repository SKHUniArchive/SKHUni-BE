package com.skhuni.skhunibackend.image.domain.repository;

import com.skhuni.skhunibackend.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
