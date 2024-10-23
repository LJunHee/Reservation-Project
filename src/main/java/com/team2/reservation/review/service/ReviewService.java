package com.team2.reservation.review.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.team2.reservation.review.model.ReviewDao;
import com.team2.reservation.review.model.ReviewVo;

@Service
public class ReviewService {
    private final ReviewDao reviewDao;
    
    @Autowired
    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }
    
    // 리뷰 추가
    public void add(ReviewVo bean) {
        reviewDao.addReview(bean);
    }
    
    // 식당별 리뷰 목록
    public void listByRestaurant(int restNo, Model model) {
        model.addAttribute("reviewList", reviewDao.getReviewsByRestaurant(restNo));
    }
    
    // 사용자별 리뷰 목록
    public void listByUser(int userNo, Model model) {
        model.addAttribute("reviewList", reviewDao.getReviewsByUser(userNo));
    }
    
    // 리뷰 상세 정보
    public ReviewVo detail(int reviewNo) {
        return reviewDao.getReview(reviewNo);
    }
    
    // 리뷰 수정
    public void update(ReviewVo bean) {
        reviewDao.updateReview(bean);
    }
    
    // 리뷰 삭제
    public void delete(int reviewNo) {
        reviewDao.deleteReview(reviewNo);
    }
}