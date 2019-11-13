package xyz.bolitao.tmall_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.Review;

import java.util.List;

/**
 * @author 陶波利
 */
public interface ReviewDAO extends JpaRepository<Review, Integer> {
    /**
     * 获得产品评论的降序 list
     *
     * @param product product
     * @return 产品评论的降序 list
     */
    List<Review> findByProductOrderByIdDesc(Product product);

    /**
     * 获得产品评价数
     *
     * @param product product
     * @return 产品评价数
     */
    int countByProduct(Product product);
}
