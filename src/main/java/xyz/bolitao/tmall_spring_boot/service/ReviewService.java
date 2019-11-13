package xyz.bolitao.tmall_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bolitao.tmall_spring_boot.dao.ReviewDAO;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.Review;

import java.util.List;

/**
 * @author 陶波利
 */
@Service
public class ReviewService {
    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    ProductService productService;

    public void add(Review review) {
        reviewDAO.save(review);
    }

    public List<Review> list(Product product) {
        return reviewDAO.findByProductOrderByIdDesc(product);
    }

    public int getCount(Product product) {
        return reviewDAO.countByProduct(product);
    }
}