package xyz.bolitao.tmall_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bolitao.tmall_spring_boot.dao.OrderItemDAO;
import xyz.bolitao.tmall_spring_boot.pojo.Order;
import xyz.bolitao.tmall_spring_boot.pojo.OrderItem;

import java.util.List;

/**
 * @author 陶波利
 */
@Service
public class OrderItemService {
    @Autowired
    OrderItemDAO orderItemDAO;
    @Autowired
    ProductImageService productImageService;

    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    public void fill(Order order) {
        List<OrderItem> orderItems = listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : orderItems) {
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
            productImageService.setFirstProductImage(oi.getProduct());
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }

    public List<OrderItem> listByOrder(Order order) {
        return orderItemDAO.findByOrderOrderByIdDesc(order);
    }

}