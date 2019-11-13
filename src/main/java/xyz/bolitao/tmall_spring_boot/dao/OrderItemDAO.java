package xyz.bolitao.tmall_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bolitao.tmall_spring_boot.pojo.Order;
import xyz.bolitao.tmall_spring_boot.pojo.OrderItem;
import xyz.bolitao.tmall_spring_boot.pojo.Product;

import java.util.List;

/**
 * @author 陶波利
 */
public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

    List<OrderItem> findByProduct(Product product);
}