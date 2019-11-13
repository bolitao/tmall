package xyz.bolitao.tmall_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bolitao.tmall_spring_boot.pojo.Order;
import xyz.bolitao.tmall_spring_boot.pojo.User;

import java.util.List;

/**
 * @author 陶波利
 */
public interface OrderDAO extends JpaRepository<Order, Integer> {
    /**
     * 返回 user 状态不为 status 的订单 list
     *
     * @param user   user
     * @param status status
     * @return 某用户的订单 list
     */
    public List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}