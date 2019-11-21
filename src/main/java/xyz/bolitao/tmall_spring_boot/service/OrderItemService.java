package xyz.bolitao.tmall_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bolitao.tmall_spring_boot.dao.OrderItemDAO;
import xyz.bolitao.tmall_spring_boot.pojo.Order;
import xyz.bolitao.tmall_spring_boot.pojo.OrderItem;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.User;

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

    public List<OrderItem> listByOrder(Order order) {
        return orderItemDAO.findByOrderOrderByIdDesc(order);
    }

    public void fill(Order order) {
        List<OrderItem> orderItems = listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            totalNumber += orderItem.getNumber();
            productImageService.setFirstProductImage(orderItem.getProduct());
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }

    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    public void update(OrderItem orderItem) {
        orderItemDAO.save(orderItem);
    }

    public OrderItem get(int id) {
        return orderItemDAO.findById(id).get();
    }

    public void delete(int id) {
        orderItemDAO.deleteById(id);
    }

    public List<OrderItem> listByProduct(Product product) {
        return orderItemDAO.findByProduct(product);
    }

    public int getSaleCount(Product product) { // TODO: L
        List<OrderItem> orderItems = listByProduct(product);
        int result = 0;
        for (OrderItem orderItem : orderItems) {
            if (null != orderItem.getOrder()) {
                if (null != orderItem.getOrder() && null != orderItem.getOrder().getPayDate()) {
                    result += orderItem.getNumber();
                }
            }
        }
        return result;
    }

    public void add(OrderItem orderItem) {
        orderItemDAO.save(orderItem);
    }

    public List<OrderItem> listByUser(User user) {
        return orderItemDAO.findByUserAndOrderIsNull(user);
    }
}