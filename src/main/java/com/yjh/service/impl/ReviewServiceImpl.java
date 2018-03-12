package com.yjh.service.impl;

import com.yjh.mapper.ReviewMapper;
import com.yjh.pojo.*;
import com.yjh.service.ReviewService;
import com.yjh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserService userService;

    @Override
    public int add(Review review) {
        return reviewMapper.insert(review);
    }

    @Override
    public int delete(int id) {
        return reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Review review) {
        return reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        Review review = reviewMapper.selectByPrimaryKey(id);
        setUser(review);
        return review;
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id");
        List<Review> reviews = reviewMapper.selectByExample(example);
        setUser(reviews);
        return reviews;
    }

    @Override
    public int getReviewCount(int pid) {
        return list(pid).size();
    }

    private void setUser(Review review) {
        User user = userService.get(review.getUid());
        review.setUser(user);
    }

    private void setUser(List<Review> reviews) {
        for (Review review : reviews)
            setUser(review);
    }
}
