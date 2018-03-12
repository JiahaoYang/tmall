package com.yjh.service;

import com.yjh.pojo.Review;

import java.util.List;

public interface ReviewService {
    int add(Review review);

    int delete(int id);

    int update(Review review);

    Review get(int id);

    List<Review> list(int pid);

    int getReviewCount(int pid);
}
