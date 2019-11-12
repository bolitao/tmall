package xyz.bolitao.tmall_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bolitao.tmall_spring_boot.pojo.Order;

/**
 * @author 陶波利
 */
public interface OrderDAO extends JpaRepository<Order, Integer> {
}