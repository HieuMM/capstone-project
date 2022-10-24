package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByOwnPitch_Id(Long id);
    @Query("SELECT COALESCE((SUM(r.rate) / COUNT(r.rate))*10 , 0) AS avgReview FROM Review r WHERE r.ownPitch.id = ?1")
     double avgReview(Long id);
}
