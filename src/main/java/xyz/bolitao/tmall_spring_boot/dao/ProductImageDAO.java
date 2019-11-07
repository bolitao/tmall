package xyz.bolitao.tmall_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.ProductImage;

import java.util.List;

/**
 * @author 陶波利
 */
public interface ProductImageDAO extends JpaRepository<ProductImage, Integer> {
    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
